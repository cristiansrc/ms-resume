package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.ILabelService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ILabelRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ILabelMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.LabelEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LabelService implements ILabelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelService.class);
    private final ILabelRepository repository;
    private final ILabelMapper mapper;
    private final MessageResolver messageResolver;
    private final IHomeRepository homeRepo;

    @Transactional(readOnly = true)
    @Override
    public List<LabelResponse> labelGet() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching all labels");
        }
        final List<LabelEntity> listEntities = repository.findAll();
        final List<LabelResponse> listResponse = mapper.labelToLabelResponse(listEntities);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetched {} labels", listResponse.size());
        }
        return listResponse;
    }

    @Transactional
    @Override
    public void labelIdDelete(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleting label with id: {}", identifier);
        }
        final LabelEntity entity = entityById(identifier);

        if (homeRepo.existsByLabelIdAndDeletedFalse(identifier)) {
            throw messageResolver.preconditionFailed("label.delete.precondition.failed", identifier);
        }

        repository.delete(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Label with id: {} deleted successfully", identifier);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public LabelResponse labelIdGet(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching label with id: {}", identifier);
        }
        final LabelEntity entity = entityById(identifier);
        final LabelResponse response = mapper.labelToLabelResponse(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetched label with id: {}", identifier);
        }
        return response;
    }

    @Transactional
    @Override
    public ImageUrlPost201Response labelPost(final LabelRequest labelRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new label with name: {}", labelRequest.getName());
        }
        LabelEntity labelEntity = mapper.labelToLabelEntity(labelRequest);
        labelEntity = repository.save(labelEntity);
        final ImageUrlPost201Response response = new ImageUrlPost201Response();
        response.setId(labelEntity.getId());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Created label with id: {}", labelEntity.getId());
        }
        return response;
    }

    private LabelEntity entityById(final Long identifier) {
        return repository.findById(identifier)
                .orElseThrow(() -> messageResolver.notFound("label.not.found", identifier));
    }
}
