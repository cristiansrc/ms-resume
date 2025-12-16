package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillTypeRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ISkillTypeMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillTypeEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class SkillTypeServiceTest {

    @Mock
    private ISkillTypeRepository skillTypeRepository;

    @Mock
    private ISkillTypeMapper skillTypeMapper;

    @Mock
    private ISkillRepository skillRepository;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private SkillTypeService skillTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void skillTypeGet() {
        when(skillTypeRepository.findAllNotDeleted()).thenReturn(Collections.singletonList(new SkillTypeEntity()));
        when(skillTypeMapper.toResponsetList(any())).thenReturn(Collections.singletonList(new SkillTypeResponse()));

        List<SkillTypeResponse> response = skillTypeService.skillTypeGet();

        assertNotNull(response);
    }

    @Test
    void skillTypeIdDelete() {
        SkillTypeEntity entity = new SkillTypeEntity();
        when(skillTypeRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(skillTypeRepository.save(any())).thenReturn(entity);

        skillTypeService.skillTypeIdDelete(1L);
    }

    @Test
    void skillTypeIdGet() {
        SkillTypeEntity entity = new SkillTypeEntity();
        when(skillTypeRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(skillTypeMapper.toResponse(entity)).thenReturn(new SkillTypeResponse());

        SkillTypeResponse response = skillTypeService.skillTypeIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void skillTypeIdPut() {
        SkillTypeEntity entity = new SkillTypeEntity();
        entity.setSkills(new ArrayList<>());
        SkillTypeRequest request = new SkillTypeRequest();
        request.setSkillIds(Collections.singletonList(1L));
        when(skillTypeRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(skillRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new SkillEntity()));
        doNothing().when(skillTypeMapper).updateEntity(any(), any());
        when(skillTypeRepository.save(any())).thenReturn(entity);

        skillTypeService.skillTypeIdPut(1L, request);
    }

    @Test
    void skillTypePost() {
        SkillTypeEntity entity = new SkillTypeEntity();
        entity.setSkills(new ArrayList<>());
        SkillTypeRequest request = new SkillTypeRequest();
        request.setSkillIds(Collections.singletonList(1L));
        when(skillTypeMapper.toEntity(request)).thenReturn(entity);
        when(skillRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new SkillEntity()));
        when(skillTypeRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = skillTypeService.skillTypePost(request);

        assertNotNull(response);
    }

    @Test
    void getEntityById_notFound() {
        when(skillTypeRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("skill.type.not.found"));

        assertThrows(RuntimeException.class, () -> skillTypeService.skillTypeIdGet(1L));
    }

    @Test
    void saveEntity_skillNotFound() {
        SkillTypeRequest request = new SkillTypeRequest();
        request.setSkillIds(Collections.singletonList(1L));
        when(skillRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("skill.not.found"));

        assertThrows(RuntimeException.class, () -> skillTypeService.skillTypePost(request));
    }
}
