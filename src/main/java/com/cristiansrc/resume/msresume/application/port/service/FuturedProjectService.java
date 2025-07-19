package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.exception.ResourceNotFoundException;
import com.cristiansrc.resume.msresume.application.port.interactor.IFuturedProjectService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IExperienceRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IFuturedProjectRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IFuturedProjectMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.FuturedProjectEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FuturedProjectService implements IFuturedProjectService {

    private final IFuturedProjectRepository futuredProjectRepository;
    private final IFuturedProjectMapper futuredProjectMapper;
    private final IExperienceRepository experienceRepository;
    private final IImageUrlRepository imageUrlRepository;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public List<FuturedProjectResponse> futuredProjectGet() {
        log.info("Fetching all futured projects");
        List<FuturedProjectResponse> futuredProjects = futuredProjectRepository.findAllByDeletedFalse()
                .stream()
                .map(futuredProjectMapper::toFuturedProjectResponse)
                .toList();
        log.info("Fetched {} futured projects", futuredProjects.size());
        return futuredProjects;
    }

    @Transactional
    @Override
    public void futuredProjectIdDelete(Long id) {
        log.info("Deleting futured project with id: {}", id);
        var entity = getEntityById(id);
        entity.setDeleted(true);
        futuredProjectRepository.save(entity);
        log.info("Futured project with id: {} deleted successfully", id);
    }

    @Transactional(readOnly = true)
    @Override
    public FuturedProjectResponse futuredProjectIdGet(Long id) {
        log.info("Fetching futured project with id: {}", id);
        var futuredProjectEntity = getEntityById(id);
        var futuredProjectResponse = futuredProjectMapper.toFuturedProjectResponse(futuredProjectEntity);
        log.info("Fetched futured project with id: {}", id);
        return futuredProjectResponse;
    }

    @Transactional
    @Override
    public void futuredProjectIdPut(Long id, FuturedProjectRequest futuredProjectRequest) {
        log.info("Updating futured project with id: {}", id);
        var entity = getEntityById(id);
        futuredProjectMapper.updateFuturedProjectEntityFromRequest(futuredProjectRequest, entity);
        save(entity, futuredProjectRequest);
        log.info("Futured project with id: {} updated successfully", id);
    }

    @Transactional
    @Override
    public ImageUrlPost201Response futuredProjectPost(FuturedProjectRequest futuredProjectRequest) {
        log.info("Creating new futured project");
        var entity = futuredProjectMapper.toFuturedProjectEntity(futuredProjectRequest);
        var id = save(entity, futuredProjectRequest);
        var response = new ImageUrlPost201Response();
        response.setId(id);
        log.info("New futured project created with id: {}", id);
        return response;
    }

    private FuturedProjectEntity getEntityById(Long id) {
        return futuredProjectRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> messageResolver.notFound("futured.project.not.found", id));
    }

    private Long save(FuturedProjectEntity entity, FuturedProjectRequest futuredProjectRequest) {
        var experience = experienceRepository.findByIdAndDeletedFalse(futuredProjectRequest.getExperienceId())
                .orElseThrow(() ->
                        messageResolver.notFound("experience.not.found", futuredProjectRequest.getExperienceId()));

        var imageListUrl = imageUrlRepository.findByIdAndDeletedFalse(futuredProjectRequest.getImageUrlId())
                .orElseThrow(() ->
                        messageResolver.notFound("image.list.url.not.found", futuredProjectRequest.getImageListUrlId()));

        var imageUrl = imageUrlRepository.findByIdAndDeletedFalse(futuredProjectRequest.getImageUrlId())
                .orElseThrow(() ->
                        messageResolver.notFound("image.url.not.found", futuredProjectRequest.getImageUrlId()));

        entity.setExperience(experience);
        entity.setImageListUrl(imageListUrl);
        entity.setImageUrl(imageUrl);
        var returnEntity = futuredProjectRepository.save(entity);
        return returnEntity.getId();
    }
}
