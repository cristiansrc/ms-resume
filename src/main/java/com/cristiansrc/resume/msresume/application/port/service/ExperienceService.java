package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IExperienceService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IExperienceRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillSonRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IExperienceMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceSkillEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExperienceService implements IExperienceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceService.class);
    private final IExperienceRepository repository;
    private final IExperienceMapper mapper;
    private final ISkillSonRepository skillSonRepo;
    private final MessageResolver messageResolver;


    @Transactional(readOnly = true)
    @Override
    public List<ExperienceResponse> experienceGet() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetching all experiences");
        }
        final List<ExperienceEntity> experienceEntities = repository.findAllByDeletedFalse();

        // allow returning empty list instead of 404
        final List<ExperienceResponse> experienceResponses = mapper.toExperienceResponseList(experienceEntities);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetched {} experiences", experienceResponses.size());
        }
        return experienceResponses;
    }

    @Transactional(readOnly = true)
    @Override
    public ExperienceResponse experienceIdGet(final Long identifier) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetching experience with id: {}", identifier);
        }
        final ExperienceEntity experienceEntity = getEntityById(identifier);
        final ExperienceResponse experienceResponse = mapper.toExperienceResponse(experienceEntity);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetched experience with id: {}", identifier);
        }
        return experienceResponse;
    }

    @Transactional
    @Override
    public void experienceIdPut(final Long identifier, final ExperienceRequest experienceRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updating experience with id: {}", identifier);
        }
        final ExperienceEntity experienceEntity = getEntityById(identifier);
        mapper.updateExperienceEntityFromRequest(experienceRequest, experienceEntity);

        final List<SkillSonEntity> newSkillSonEntities = getSkillSons(experienceRequest.getSkillSonIds());

        if (experienceEntity.getExperienceSkills() == null) {
            experienceEntity.setExperienceSkills(new ArrayList<>());
        } else {
            experienceEntity.getExperienceSkills().clear();
        }

        repository.saveAndFlush(experienceEntity);

        for (final SkillSonEntity skillSon : newSkillSonEntities) {
            final ExperienceSkillEntity experienceSkill = new ExperienceSkillEntity();
            experienceSkill.setExperience(experienceEntity);
            experienceSkill.setSkillSon(skillSon);
            experienceEntity.getExperienceSkills().add(experienceSkill);
        }

        repository.save(experienceEntity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updated experience with id: {}", identifier);
        }
    }

    @Transactional
    @Override
    public ImageUrlPost201Response experiencePost(final ExperienceRequest experienceRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new experience");
        }
        final ExperienceEntity experienceEntity = mapper.toExperienceEntity(experienceRequest);

        final List<SkillSonEntity> newSkillSonEntities = getSkillSons(experienceRequest.getSkillSonIds());

        if (experienceEntity.getExperienceSkills() == null) {
            experienceEntity.setExperienceSkills(new ArrayList<>());
        }

        for (final SkillSonEntity skillSon : newSkillSonEntities) {
            final ExperienceSkillEntity experienceSkill = new ExperienceSkillEntity();
            experienceSkill.setExperience(experienceEntity);
            experienceSkill.setSkillSon(skillSon);
            experienceEntity.getExperienceSkills().add(experienceSkill);
        }

        final ExperienceEntity savedEntity = repository.save(experienceEntity);
        final ImageUrlPost201Response imageUrlResponse = new ImageUrlPost201Response();
        imageUrlResponse.setId(savedEntity.getId());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Created new experience with id: {}", savedEntity.getId());
        }
        return imageUrlResponse;
    }

    @Transactional
    @Override
    public void experienceIdDelete(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleting experience with id: {}", identifier);
        }
        final ExperienceEntity experienceEntity = getEntityById(identifier);
        experienceEntity.setDeleted(true);
        repository.save(experienceEntity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleted experience with id: {}", identifier);
        }
    }

    private List<SkillSonEntity> getSkillSons(final List<Long> skillSonIds) {
        if (skillSonIds == null) {
            return Collections.emptyList();
        }
        return skillSonIds.stream()
                .map(skillSonId -> skillSonRepo.findByIdAndDeletedFalse(skillSonId)
                        .orElseThrow(() -> messageResolver.notFound("skill.not.found", skillSonId)))
                .toList();
    }

    private ExperienceEntity getEntityById(final Long identifier) {
        return repository.findByIdAndDeletedFalse(identifier)
                .orElseThrow(() -> messageResolver.notFound("experience.not.found.byid", identifier));
    }
}
