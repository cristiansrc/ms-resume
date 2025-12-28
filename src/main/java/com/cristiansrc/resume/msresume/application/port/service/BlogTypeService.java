package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IBlogTypeService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBlogTypeRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBlogTypeMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogTypeEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogTypeService implements IBlogTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogTypeService.class);
    private final IBlogTypeRepository repository;
    private final IBlogTypeMapper mapper;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public List<BlogTypeResponse> blogTypeGet() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching all blog types");
        }
        final List<BlogTypeEntity> entities = repository.findAllByDeletedFalse();
        final List<BlogTypeResponse> models = mapper.toResponseList(entities);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Found {} blog types", models.size());
        }
        return models;
    }

    @Transactional
    @Override
    public void blogTypeIdDelete(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleting blog type with id: {}", identifier);
        }
        final BlogTypeEntity entity = getEntityById(identifier);
        entity.setDeleted(true);
        repository.save(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Blog type with id: {} deleted successfully", identifier);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public BlogTypeResponse blogTypeIdGet(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching blog type with id: {}", identifier);
        }
        final BlogTypeEntity entity = getEntityById(identifier);
        final BlogTypeResponse model = mapper.toResponse(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Found blog type with id: {}", identifier);
        }
        return model;
    }

    @Transactional
    @Override
    public void blogTypeIdPut(final Long identifier, final BlogTypeRequest blogTypeRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updating blog type with id: {}", identifier);
        }
        final BlogTypeEntity entity = getEntityById(identifier);
        mapper.updateEntity(blogTypeRequest, entity);
        repository.save(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Blog type with id: {} updated successfully", identifier);
        }
    }

    @Transactional
    @Override
    public ImageUrlPost201Response blogTypePost(final BlogTypeRequest blogTypeRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new blog type");
        }
        final BlogTypeEntity entity = mapper.toEntity(blogTypeRequest);
        final BlogTypeEntity savedEntity = repository.save(entity);
        final ImageUrlPost201Response response = new ImageUrlPost201Response();
        response.setId(savedEntity.getId());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Blog type with id: {} created successfully", savedEntity.getId());
        }
        return response;
    }

    private BlogTypeEntity getEntityById(final Long identifier) {
        return repository.findByIdAndDeletedFalse(identifier)
                .orElseThrow(() -> messageResolver.notFound("blog.type.not.found", identifier));

    }
}
