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
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.LabelEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeService implements IHomeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeService.class);
    private final IHomeRepository repository;
    private final IHomeMapper mapper;
    private final IImageUrlRepository imageUrlRepo;
    private final ILabelRepository labelRepo;
    private final MessageResolver messageResolver;
    private final IS3Service s3Service;

    @Transactional(readOnly = true)
    @Override
    public HomeResponse homeIdGet(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching home with id: {}", identifier);
        }
        final HomeEntity homeEntity = getEntityById(identifier);
        final HomeResponse homeResponse = mapper.toResponse(homeEntity, s3Service);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetched home with id: {}", identifier);
        }
        return homeResponse;
    }

    @Transactional
    @Override
    public void homeIdPut(final Long identifier, final HomeRequest homeRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updating home with id: {}", identifier);
        }
        final HomeEntity homeEntity = getEntityById(identifier);

        mapper.updateEntityFromRequest(homeRequest, homeEntity);

        updateImage(homeRequest.getImageUrlId(), homeEntity);
        updateLabels(homeRequest.getLabelIds(), homeEntity);

        repository.save(homeEntity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updated home with id: {}", identifier);
        }
    }

    private void updateImage(final Long imageId, final HomeEntity homeEntity) {
        if (imageId != null) {
            final ImageUrlEntity imagen = imageUrlRepo.findByIdAndDeletedFalse(imageId)
                    .orElseThrow(() -> messageResolver.notFound("image.home.not.found", imageId));
            homeEntity.setImageUrl(imagen);
        }
    }

    private void updateLabels(final List<Long> labelIds, final HomeEntity homeEntity) {
        if (labelIds == null) {
            return;
        }

        homeEntity.getHomeLabelRelations().clear();
        repository.flush();

        final Set<Long> uniqueLabelIds = new HashSet<>(labelIds);

        final List<LabelEntity> labels = labelRepo.findAllById(uniqueLabelIds);

        if (labels.size() != uniqueLabelIds.size()) {
            final Set<Long> foundIds = labels.stream().map(LabelEntity::getId).collect(Collectors.toSet());
            final Long missingId = uniqueLabelIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .findFirst()
                    .orElse(null);
            throw messageResolver.notFound("label.not.found", missingId);
        }

        for (final LabelEntity label : labels) {
            final HomeLabelRelationalEntity relation = new HomeLabelRelationalEntity();
            relation.setHome(homeEntity);
            relation.setLabel(label);
            homeEntity.getHomeLabelRelations().add(relation);
        }
    }

    private HomeEntity getEntityById(final Long identifier) {
        return repository.findById(identifier)
                .orElseThrow(() -> messageResolver.notFound("home.not.found.byid", identifier));
    }
}
