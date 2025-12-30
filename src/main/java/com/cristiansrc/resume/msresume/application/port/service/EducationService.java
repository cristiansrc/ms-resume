package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IEducationService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IEducationRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IEducationMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.EducationEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EducationService implements IEducationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EducationService.class);
    private final IEducationRepository repository;
    private final IEducationMapper mapper;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public List<EducationResponse> educationGet() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetching all educations");
        }
        final List<EducationEntity> educationEntities = repository.findAllByDeletedFalse();
        final List<EducationResponse> educationResponses = mapper.toEducationResponseList(educationEntities);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetched {} educations", educationResponses.size());
        }
        return educationResponses;
    }

    @Transactional(readOnly = true)
    @Override
    public EducationResponse educationIdGet(final Long identifier) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetching education with id: {}", identifier);
        }
        final EducationEntity educationEntity = getEntityById(identifier);
        final EducationResponse educationResponse = mapper.toEducationResponse(educationEntity);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetched education with id: {}", identifier);
        }
        return educationResponse;
    }

    @Transactional
    @Override
    public void educationIdPut(final Long identifier, final EducationRequest educationRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updating education with id: {}", identifier);
        }
        final EducationEntity educationEntity = getEntityById(identifier);
        mapper.updateEducationEntityFromRequest(educationRequest, educationEntity);
        repository.save(educationEntity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updated education with id: {}", identifier);
        }
    }

    @Transactional
    @Override
    public ImageUrlPost201Response educationPost(final EducationRequest educationRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new education");
        }
        final EducationEntity educationEntity = mapper.toEducationEntity(educationRequest);
        final EducationEntity savedEntity = repository.save(educationEntity);
        final ImageUrlPost201Response imageUrlResponse = new ImageUrlPost201Response();
        imageUrlResponse.setId(savedEntity.getId());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Created new education with id: {}", savedEntity.getId());
        }
        return imageUrlResponse;
    }

    @Transactional
    @Override
    public void educationIdDelete(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleting education with id: {}", identifier);
        }
        final EducationEntity educationEntity = getEntityById(identifier);
        educationEntity.setDeleted(true);
        repository.save(educationEntity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleted education with id: {}", identifier);
        }
    }

    private EducationEntity getEntityById(final Long identifier) {
        return repository.findByIdAndDeletedFalse(identifier)
                .orElseThrow(() -> messageResolver.notFound("education.not.found.byid", identifier));
    }
}
