package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.ISkillService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillSonRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ISkillMapper;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ISkillSonMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SkillService implements ISkillService {

    private final ISkillRepository skillRepository;
    private final ISkillSonRepository skillSonRepository;
    private final ISkillMapper skillMapper;
    private final ISkillSonMapper skillSonMapper;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public List<SkillResponse> skillGet() {
        log.info("Fetching all skills");
        var entities = skillRepository.findAllByDeletedFalse();
        var models = skillMapper.toResponseList(entities);
        log.info("Found {} skills", models.size());
        return models;
    }

    @Transactional
    @Override
    public void skillIdDelete(Long id) {
        log.info("Deleting skill with ID: {}", id);
        var skillEntity = entityById(id);
        skillEntity.setDeleted(true);
        skillRepository.save(skillEntity);
        log.info("Skill with ID: {} deleted successfully", id);
    }

    @Transactional(readOnly = true)
    @Override
    public SkillResponse skillIdGet(Long id) {
        log.info("Fetching skill with ID: {}", id);
        var skillEntity = entityById(id);
        var skillResponse = skillMapper.toResponse(skillEntity);
        log.info("Found skill: {}", skillResponse);
        return skillResponse;
    }

    @Transactional
    @Override
    public void skillIdPut(Long id, SkillRequest skillRequest) {
        log.info("Updating skill with ID: {}", id);
        var skillEntity = entityById(id);
        skillMapper.updateEntity(skillRequest, skillEntity);
        saveEntity(skillEntity, skillRequest.getSkillSonIds());
        log.info("Skill with ID: {} updated successfully", id);
    }

    @Transactional
    @Override
    public ImageUrlPost201Response skillPost(SkillRequest skillRequest) {
        log.info("Creating new skill with request: {}", skillRequest);
        var skillEntity = skillMapper.toEntity(skillRequest);
        var id = saveEntity(skillEntity, skillRequest.getSkillSonIds());
        log.info("Skill created with ID: {}", skillEntity.getId());
        var response = new ImageUrlPost201Response();
        response.setId(id);
        return response;
    }

    private SkillEntity entityById(Long id) {
        return skillRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> messageResolver.notFound("skill.not.found", id));


    }

    private Long saveEntity(SkillEntity skillEntity, List<Long> skillSonIds) {
        var SkillSonEntities = skillSonIds.stream()
                .map( skillSinId -> skillSonRepository.findByIdAndDeletedFalse(skillSinId)
                        .orElseThrow(() -> messageResolver.notFound("skill.son.not.found", skillSinId)))
                .toList();

        skillEntity.getSkillSons().clear();
        skillEntity.getSkillSons().addAll(SkillSonEntities);
        var saveEntity = skillRepository.save(skillEntity);
        return saveEntity.getId();
    }
}
