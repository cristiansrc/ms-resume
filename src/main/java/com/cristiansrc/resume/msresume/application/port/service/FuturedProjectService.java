package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IFuturedProjectService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IExperienceRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IFuturedProjectRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IFuturedProjectMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.FuturedProjectEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FuturedProjectService implements IFuturedProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuturedProjectService.class);
    private final IFuturedProjectRepository repository;
    private final IFuturedProjectMapper mapper;
    private final IExperienceRepository experienceRepo;
    private final IImageUrlRepository imageUrlRepo;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public List<FuturedProjectResponse> futuredProjectGet() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching all futured projects");
        }
        final List<FuturedProjectResponse> futuredProjects = repository.findAllByDeletedFalse()
                .stream()
                .map(mapper::toFuturedProjectResponse)
                .toList();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetched {} futured projects", futuredProjects.size());
        }
        return futuredProjects;
    }

    @Transactional
    @Override
    public void futuredProjectIdDelete(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleting futured project with id: {}", identifier);
        }
        final FuturedProjectEntity entity = getEntityById(identifier);
        entity.setDeleted(true);
        repository.save(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Futured project with id: {} deleted successfully", identifier);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public FuturedProjectResponse futuredProjectIdGet(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching futured project with id: {}", identifier);
        }
        final FuturedProjectEntity futuredProjectEntity = getEntityById(identifier);
        final FuturedProjectResponse futuredProjectResponse = mapper.toFuturedProjectResponse(futuredProjectEntity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetched futured project with id: {}", identifier);
        }
        return futuredProjectResponse;
    }

    @Transactional
    @Override
    public void futuredProjectIdPut(final Long identifier, final FuturedProjectRequest futuredProjectRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updating futured project with id: {}", identifier);
        }
        final FuturedProjectEntity entity = getEntityById(identifier);
        mapper.updateFuturedProjectEntityFromRequest(futuredProjectRequest, entity);
        save(entity, futuredProjectRequest);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Futured project with id: {} updated successfully", identifier);
        }
    }

    @Transactional
    @Override
    public ImageUrlPost201Response futuredProjectPost(final FuturedProjectRequest futuredProjectRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new futured project");
        }
        final FuturedProjectEntity entity = mapper.toFuturedProjectEntity(futuredProjectRequest);
        final Long identifier = save(entity, futuredProjectRequest);
        final ImageUrlPost201Response response = new ImageUrlPost201Response();
        response.setId(identifier);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("New futured project created with id: {}", identifier);
        }
        return response;
    }

    private FuturedProjectEntity getEntityById(final Long identifier) {
        return repository.findByIdAndDeletedFalse(identifier)
                .orElseThrow(() -> messageResolver.notFound("futured.project.not.found", identifier));
    }

    private Long save(final FuturedProjectEntity entity, final FuturedProjectRequest futuredProjectRequest) {
        final ExperienceEntity experience = experienceRepo.findByIdAndDeletedFalse(futuredProjectRequest.getExperienceId())
                .orElseThrow(() ->
                        messageResolver.notFound("experience.not.found", futuredProjectRequest.getExperienceId()));

        final ImageUrlEntity imageListUrl = imageUrlRepo.findByIdAndDeletedFalse(futuredProjectRequest.getImageUrlId())
                .orElseThrow(() ->
                        messageResolver.notFound("image.list.url.not.found", futuredProjectRequest.getImageListUrlId()));

        final ImageUrlEntity imageUrl = imageUrlRepo.findByIdAndDeletedFalse(futuredProjectRequest.getImageUrlId())
                .orElseThrow(() ->
                        messageResolver.notFound("image.url.not.found", futuredProjectRequest.getImageUrlId()));

        entity.setExperience(experience);
        entity.setImageListUrl(imageListUrl);
        entity.setImageUrl(imageUrl);
        final FuturedProjectEntity returnEntity = repository.save(entity);
        return returnEntity.getId();
    }
}
