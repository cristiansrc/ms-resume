package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IHomeService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IHomeMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class HomeService implements IHomeService {
    private final IHomeRepository homeRepository;
    private final IHomeMapper homeMapper;
    private final IImageUrlRepository imageUrlRepository;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public HomeResponse homeIdGet(Long id) {
        log.info("Fetching home with id: {}", id);
        var homeEntity = getEntityById(id);
        var homeResponse = homeMapper.toResponse(homeEntity);
        log.info("Fetched home with id: {}", id);
        return homeResponse;
    }

    @Transactional
    @Override
    public void homeIdPut(Long id, HomeRequest homeRequest) {
        log.info("Updating home with id: {}", id);
        var homeEntity = getEntityById(id);
        homeMapper.updateEntityFromRequest(homeRequest, homeEntity);

        var imagen = imageUrlRepository.findByIdAndDeletedFalse(homeEntity.getId())
                .orElseThrow(() -> messageResolver.notFound("image.home.not.found", id));

        homeEntity.setImageUrl(imagen);
        homeRepository.save(homeEntity);
        log.info("Updated home with id: {}", id);
    }

    private HomeEntity getEntityById(Long id) {
        return homeRepository.findById(id)
                .orElseThrow(() -> messageResolver.notFound("home.not.found.byid", id));
    }
}
