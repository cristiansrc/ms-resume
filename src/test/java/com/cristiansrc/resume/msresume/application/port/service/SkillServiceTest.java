package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillSonRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ISkillMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonEntity;
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

class SkillServiceTest {

    @Mock
    private ISkillRepository skillRepository;

    @Mock
    private ISkillSonRepository skillSonRepository;

    @Mock
    private ISkillMapper skillMapper;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private SkillService skillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void skillGet() {
        when(skillRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(new SkillEntity()));
        when(skillMapper.toResponseList(any())).thenReturn(Collections.singletonList(new SkillResponse()));

        List<SkillResponse> response = skillService.skillGet();

        assertNotNull(response);
    }

    @Test
    void skillIdDelete() {
        SkillEntity entity = new SkillEntity();
        when(skillRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(skillRepository.save(any())).thenReturn(entity);

        skillService.skillIdDelete(1L);
    }

    @Test
    void skillIdGet() {
        SkillEntity entity = new SkillEntity();
        when(skillRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(skillMapper.toResponse(entity)).thenReturn(new SkillResponse());

        SkillResponse response = skillService.skillIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void skillIdPut() {
        SkillEntity entity = new SkillEntity();
        entity.setSkillSons(new ArrayList<>());
        SkillRequest request = new SkillRequest();
        request.setSkillSonIds(Collections.singletonList(1L));
        when(skillRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new SkillSonEntity()));
        doNothing().when(skillMapper).updateEntity(any(), any());
        when(skillRepository.save(any())).thenReturn(entity);

        skillService.skillIdPut(1L, request);
    }

    @Test
    void skillPost() {
        SkillEntity entity = new SkillEntity();
        entity.setId(1L);
        entity.setSkillSons(new ArrayList<>());
        SkillRequest request = new SkillRequest();
        request.setSkillSonIds(Collections.singletonList(1L));
        when(skillMapper.toEntity(request)).thenReturn(entity);
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new SkillSonEntity()));
        when(skillRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = skillService.skillPost(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void entityById_notFound() {
        when(skillRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("skill.not.found"));

        assertThrows(RuntimeException.class, () -> skillService.skillIdGet(1L));
    }
}
