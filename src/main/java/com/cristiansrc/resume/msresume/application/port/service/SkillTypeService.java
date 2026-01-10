package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.ISkillTypeService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillTypeRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ISkillTypeMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillTypeEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillTypeRelationalEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SkillTypeService implements ISkillTypeService {

    private final ISkillTypeRepository skillTypeRepository;
    private final ISkillTypeMapper skillTypeMapper;
    private final ISkillRepository skillRepository;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public List<SkillTypeResponse> skillTypeGet() {
        log.info("Fetching all skill types");
        var entities = skillTypeRepository.findAllNotDeleted();
        var models = skillTypeMapper.toResponsetList(entities);
        log.info("Found {} skill types", models.size());
        return models;
    }

    @Transactional
    @Override
    public void skillTypeIdDelete(Long id) {
        log.info("Deleting skill type with id: {}", id);
        var entity = getEntityById(id);
        entity.setDeleted(true);
        skillTypeRepository.save(entity);
        log.info("Skill type with id: {} deleted successfully", id);
    }

    @Transactional(readOnly = true)
    @Override
    public SkillTypeResponse skillTypeIdGet(Long id) {
        log.info("Fetching skill type with id: {}", id);
        var entity = getEntityById(id);
        var model = skillTypeMapper.toResponse(entity);
        log.info("Found skill type with id: {}", id);
        return model;
    }

    @Transactional
    @Override
    public void skillTypeIdPut(Long id, SkillTypeRequest skillTypeRequest) {
        log.info("Updating skill type with id: {}", id);
        var entity = getEntityById(id);
        skillTypeMapper.updateEntity(skillTypeRequest, entity);
        saveEntity(entity, skillTypeRequest.getSkillIds());
        log.info("Skill type with id: {} updated successfully", id);
    }

    @Transactional
    @Override
    public ImageUrlPost201Response skillTypePost(SkillTypeRequest skillTypeRequest) {
        log.info("Creating new skill type");
        var entity = skillTypeMapper.toEntity(skillTypeRequest);
        Long id = saveEntity(entity, skillTypeRequest.getSkillIds());
        log.info("Skill type created with id: {}", id);
        var response = new ImageUrlPost201Response();
        response.setId(id);
        return response;
    }

    private SkillTypeEntity getEntityById(Long id) {
        return skillTypeRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> messageResolver.notFound("skill.type.not.found", id));
    }

    private Long saveEntity(SkillTypeEntity entity, List<Long> skillIds) {

        var skillEntities = skillIds.stream()
                .map(skillId -> skillRepository.findByIdAndDeletedFalse(skillId)
                        .orElseThrow(() -> messageResolver.notFound("skill.not.found", skillId)))
                .toList();

        if (entity.getSkillTypeRelations() == null) {
            entity.setSkillTypeRelations(new ArrayList<>());
        }
        entity.getSkillTypeRelations().clear();
        
        var newRelations = skillEntities.stream()
                .map(skill -> SkillTypeRelationalEntity.builder()
                        .skill(skill)
                        .skillType(entity)
                        .build())
                .toList();
                
        entity.getSkillTypeRelations().addAll(newRelations);

        var savedEntity = skillTypeRepository.save(entity);
        return savedEntity.getId();
    }
}
