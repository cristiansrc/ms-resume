package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.ISkillSonService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillSonRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ISkillSonMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SkillSonService implements ISkillSonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillSonService.class);
    private final ISkillSonRepository repository;
    private final ISkillSonMapper mapper;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public List<SkillSonResponse> skillSonGet() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching all skill sons");
        }
        final List<SkillSonEntity> entities = repository.findAllByDeletedFalse();
        final List<SkillSonResponse> models = mapper.toRequestList(entities);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Found {} skill sons", models.size());
        }
        return models;
    }

    @Transactional
    @Override
    public void skillSonIdDelete(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleting skill son with id: {}", identifier);
        }
        final SkillSonEntity entity = getEntityById(identifier);
        entity.setDeleted(true);
        repository.save(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Skill son with id: {} deleted successfully", identifier);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public SkillSonResponse skillSonIdGet(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching skill son with id: {}", identifier);
        }
        final SkillSonEntity entity = getEntityById(identifier);
        final SkillSonResponse model = mapper.toRequest(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Found skill son with id: {}", identifier);
        }
        return model;
    }

    @Transactional
    @Override
    public void skillSonIdPut(final Long identifier, final SkillSonRequest skillSonRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updating skill son with id: {}", identifier);
        }
        final SkillSonEntity entity = getEntityById(identifier);
        mapper.updateEntity(skillSonRequest, entity);
        repository.save(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Skill son with id: {} updated successfully", identifier);
        }
    }

    @Transactional
    @Override
    public ImageUrlPost201Response skillSonPost(final SkillSonRequest skillSonRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new skill son");
        }
        final SkillSonEntity entity = mapper.toEntity(skillSonRequest);
        final SkillSonEntity savedEntity = repository.save(entity);
        final ImageUrlPost201Response response = new ImageUrlPost201Response();
        response.setId(savedEntity.getId());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Skill son with id: {} created successfully", savedEntity.getId());
        }
        return response;
    }

    private SkillSonEntity getEntityById(final Long identifier) {
        return repository.findByIdAndDeletedFalse(identifier)
                .orElseThrow(() -> messageResolver.notFound("skill.son.not.found", identifier));

    }
}
