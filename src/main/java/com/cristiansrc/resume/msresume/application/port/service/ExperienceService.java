package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.exception.ResourceNotFoundException;
import com.cristiansrc.resume.msresume.application.port.interactor.IExperienceService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IExperienceRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillSonRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IExperienceMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExperienceService implements IExperienceService {

    private final IExperienceRepository experienceRepository;
    private final IExperienceMapper experienceMapper;
    private final ISkillSonRepository skillSonRepository;
    private final MessageResolver messageResolver;


    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<List<ExperienceResponse>> experienceGet() {
        log.debug("Fetching all experiences");
        var experienceEntities = experienceRepository.findAllByDeletedFalse();

        // allow returning empty list instead of 404
        var experienceResponses = experienceMapper.toExperienceResponseList(experienceEntities);
        log.debug("Fetched {} experiences", experienceResponses.size());
        return ResponseEntity.ok(experienceResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<ExperienceResponse> experienceIdGet(Long id) {
        log.debug("Fetching experience with id: {}", id);
        var experienceEntity = getEntityById(id);
        var experienceResponse = experienceMapper.toExperienceResponse(experienceEntity);
        log.debug("Fetched experience with id: {}", id);
        return ResponseEntity.ok(experienceResponse);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> experienceIdPut(Long id, ExperienceRequest experienceRequest) {
        log.info("Updating experience with id: {}", id);
        var experienceEntity = getEntityById(id);
        experienceMapper.updateExperienceEntityFromRequest(experienceRequest, experienceEntity);
        var newSkillSonEntities = getSkillSons(experienceRequest.getSkillSonIds());
        experienceEntity.getSkillSons().clear();
        experienceEntity.getSkillSons().addAll(newSkillSonEntities);
        experienceRepository.save(experienceEntity);
        log.info("Updated experience with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Override
    public ResponseEntity<ImageUrlPost201Response> experiencePost(ExperienceRequest experienceRequest) {
        log.info("Creating new experience");
        var experienceEntity = experienceMapper.toExperienceEntity(experienceRequest);
        var newSkillSonEntities = getSkillSons(experienceRequest.getSkillSonIds());
        experienceEntity.getSkillSons().addAll(newSkillSonEntities);
        var savedEntity = experienceRepository.save(experienceEntity);
        var imageUrlResponse = new ImageUrlPost201Response();
        imageUrlResponse.setId(savedEntity.getId());
        log.info("Created new experience with id: {}", savedEntity.getId());
        return ResponseEntity.ok(imageUrlResponse);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> experienceIdDelete(Long id) {
        log.info("Deleting experience with id: {}", id);
        var experienceEntity = getEntityById(id);
        experienceEntity.setDeleted(true);
        experienceRepository.save(experienceEntity);
        log.info("Deleted experience with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    private List<SkillSonEntity> getSkillSons(List<Long> skillSonIds) {
        return skillSonIds.stream()
                .map(skillSonId -> skillSonRepository.findByIdAndDeletedFalse(skillSonId)
                        .orElseThrow(() -> messageResolver.notFound("skill.not.found", skillSonId)))
                .toList();
    }

    private ExperienceEntity getEntityById(Long id) {
        return experienceRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> messageResolver.notFound("experience.not.found.byid", id));
    }
}
