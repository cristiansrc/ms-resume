package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.ISkillService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillSonRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ISkillMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonRelationalEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SkillService implements ISkillService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillService.class);
    private final ISkillRepository repository;
    private final ISkillSonRepository skillSonRepo;
    private final ISkillMapper mapper;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public List<SkillResponse> skillGet() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching all skills");
        }
        final List<SkillEntity> entities = repository.findAllByDeletedFalse();
        final List<SkillResponse> models = mapper.toResponseList(entities);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Found {} skills", models.size());
        }
        return models;
    }

    @Transactional
    @Override
    public void skillIdDelete(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleting skill with ID: {}", identifier);
        }
        final SkillEntity skillEntity = entityById(identifier);
        skillEntity.setDeleted(true);
        repository.save(skillEntity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Skill with ID: {} deleted successfully", identifier);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public SkillResponse skillIdGet(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching skill with ID: {}", identifier);
        }
        final SkillEntity skillEntity = entityById(identifier);
        final SkillResponse skillResponse = mapper.toResponse(skillEntity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Found skill: {}", skillResponse);
        }
        return skillResponse;
    }

    @Transactional
    @Override
    public void skillIdPut(final Long identifier, final SkillRequest skillRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updating skill with ID: {}", identifier);
        }
        final SkillEntity skillEntity = entityById(identifier);
        mapper.updateEntity(skillRequest, skillEntity);
        saveEntity(skillEntity, skillRequest.getSkillSonIds());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Skill with ID: {} updated successfully", identifier);
        }
    }

    @Transactional
    @Override
    public ImageUrlPost201Response skillPost(final SkillRequest skillRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new skill with request: {}", skillRequest);
        }
        final SkillEntity skillEntity = mapper.toEntity(skillRequest);
        final Long identifier = saveEntity(skillEntity, skillRequest.getSkillSonIds());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Skill created with ID: {}", skillEntity.getId());
        }
        final ImageUrlPost201Response response = new ImageUrlPost201Response();
        response.setId(identifier);
        return response;
    }

    private SkillEntity entityById(final Long identifier) {
        return repository.findByIdAndDeletedFalse(identifier)
                .orElseThrow(() -> messageResolver.notFound("skill.not.found", identifier));
    }

    private Long saveEntity(final SkillEntity skillEntity, final List<Long> skillSonIds) {
        final List<Long> ids = skillSonIds != null ? skillSonIds : Collections.emptyList();
        skillEntity.getSkillSons().clear();
        repository.saveAndFlush(skillEntity);
        final List<SkillSonRelationalEntity> newSkillSonRelations = ids.stream()
                .map(skillSonId -> {
                    final SkillSonEntity skillSonEntity = skillSonRepo.findByIdAndDeletedFalse(skillSonId)
                            .orElseThrow(() -> messageResolver.notFound("skill.son.not.found", skillSonId));
                    final SkillSonRelationalEntity relationalEntity = new SkillSonRelationalEntity();
                    relationalEntity.setSkill(skillEntity);
                    relationalEntity.setSkillSon(skillSonEntity);
                    return relationalEntity;
                })
                .toList();

        skillEntity.getSkillSons().addAll(newSkillSonRelations);
        final SkillEntity savedEntity = repository.save(skillEntity);
        return savedEntity.getId();
    }
}
