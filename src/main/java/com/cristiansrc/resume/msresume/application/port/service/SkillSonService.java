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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SkillSonService implements ISkillSonService {

    private final ISkillSonRepository skillSonRepository;
    private final ISkillSonMapper skillSonMapper;
    private final MessageResolver messageResolver;

    @Override
    public List<SkillSonResponse> skillSonGet() {
        log.info("Fetching all skill sons");
        var entities = skillSonRepository.findAllByDeletedFalse();
        var models = skillSonMapper.toRequestList(entities);
        log.info("Found {} skill sons", models.size());
        return models;
    }

    @Override
    public void skillSonIdDelete(Long id) {
        log.info("Deleting skill son with id: {}", id);
        var entity = getEntityById(id);
        entity.setDeleted(true);
        skillSonRepository.save(entity);
        log.info("Skill son with id: {} deleted successfully", id);
    }

    @Override
    public SkillSonResponse skillSonIdGet(Long id) {
        log.info("Fetching skill son with id: {}", id);
        var entity = getEntityById(id);
        var model = skillSonMapper.toRequest(entity);
        log.info("Found skill son with id: {}", id);
        return model;
    }

    @Override
    public void skillSonIdPut(Long id, SkillSonRequest skillSonRequest) {
        log.info("Updating skill son with id: {}", id);
        var entity = getEntityById(id);
        skillSonMapper.updateEntity(skillSonRequest, entity);
        skillSonRepository.save(entity);
        log.info("Skill son with id: {} updated successfully", id);
    }

    @Override
    public ImageUrlPost201Response skillSonPost(SkillSonRequest skillSonRequest) {
        log.info("Creating new skill son");
        var entity = skillSonMapper.toEntity(skillSonRequest);
        var savedEntity = skillSonRepository.save(entity);
        var response = new ImageUrlPost201Response();
        response.setId(savedEntity.getId());
        log.info("Skill son with id: {} created successfully", savedEntity.getId());
        return response;
    }

    private SkillSonEntity getEntityById(Long id) {
        return skillSonRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> messageResolver.notFound("skill.son.not.found", id));

    }
}
