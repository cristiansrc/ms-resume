package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IHomeService;
import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ILabelRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IHomeMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeLabelRelationalEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.LabelEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class HomeService implements IHomeService {
    private final IHomeRepository homeRepository;
    private final IHomeMapper homeMapper;
    private final IImageUrlRepository imageUrlRepository;
    private final ILabelRepository labelRepository;
    private final MessageResolver messageResolver;
    private final IS3Service s3Service;

    @Transactional(readOnly = true)
    @Override
    public HomeResponse homeIdGet(Long id) {
        log.info("Fetching home with id: {}", id);
        var homeEntity = getEntityById(id);
        var homeResponse = homeMapper.toResponse(homeEntity, s3Service);
        log.info("Fetched home with id: {}", id);
        return homeResponse;
    }

    @Transactional
    @Override
    public void homeIdPut(Long id, HomeRequest homeRequest) {
        log.info("Updating home with id: {}", id);
        var homeEntity = getEntityById(id);
        
        homeMapper.updateEntityFromRequest(homeRequest, homeEntity);

        updateImage(homeRequest.getImageUrlId(), homeEntity);
        updateLabels(homeRequest.getLabelIds(), homeEntity);

        homeRepository.save(homeEntity);
        log.info("Updated home with id: {}", id);
    }

    private void updateImage(Long imageId, HomeEntity homeEntity) {
        if (imageId != null) {
            var imagen = imageUrlRepository.findByIdAndDeletedFalse(imageId)
                    .orElseThrow(() -> messageResolver.notFound("image.home.not.found", imageId));
            homeEntity.setImageUrl(imagen);
        }
    }

    private void updateLabels(List<Long> labelIds, HomeEntity homeEntity) {
        if (labelIds == null) {
            return;
        }

        homeEntity.getHomeLabelRelations().clear();
        homeRepository.flush();

        Set<Long> uniqueLabelIds = new HashSet<>(labelIds);

        var labels = labelRepository.findAllById(uniqueLabelIds);
        
        if (labels.size() != uniqueLabelIds.size()) {
            var foundIds = labels.stream().map(LabelEntity::getId).collect(Collectors.toSet());
            var missingId = uniqueLabelIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .findFirst()
                    .orElse(null);
            throw messageResolver.notFound("label.not.found", missingId);
        }
        
        for (LabelEntity label : labels) {
            var relation = new HomeLabelRelationalEntity();
            relation.setHome(homeEntity);
            relation.setLabel(label);
            homeEntity.getHomeLabelRelations().add(relation);
        }
    }

    private HomeEntity getEntityById(Long id) {
        return homeRepository.findById(id)
                .orElseThrow(() -> messageResolver.notFound("home.not.found.byid", id));
    }
}
