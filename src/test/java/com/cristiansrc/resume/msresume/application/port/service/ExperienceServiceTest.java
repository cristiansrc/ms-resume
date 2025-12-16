package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IExperienceRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillSonRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IExperienceMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceEntity;
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

class ExperienceServiceTest {

    @Mock
    private IExperienceRepository experienceRepository;

    @Mock
    private IExperienceMapper experienceMapper;

    @Mock
    private ISkillSonRepository skillSonRepository;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private ExperienceService experienceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void experienceGet() {
        when(experienceRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(new ExperienceEntity()));
        when(experienceMapper.toExperienceResponseList(any())).thenReturn(Collections.singletonList(new ExperienceResponse()));

        List<ExperienceResponse> response = experienceService.experienceGet();

        assertNotNull(response);
    }

    @Test
    void experienceIdGet() {
        ExperienceEntity entity = new ExperienceEntity();
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(experienceMapper.toExperienceResponse(entity)).thenReturn(new ExperienceResponse());

        ExperienceResponse response = experienceService.experienceIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void experienceIdPut() {
        ExperienceEntity entity = new ExperienceEntity();
        entity.setSkillSons(new ArrayList<>());
        ExperienceRequest request = new ExperienceRequest();
        request.setSkillSonIds(Collections.singletonList(1L));
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new SkillSonEntity()));
        doNothing().when(experienceMapper).updateExperienceEntityFromRequest(any(), any());
        when(experienceRepository.save(any())).thenReturn(entity);

        experienceService.experienceIdPut(1L, request);
    }

    @Test
    void experiencePost() {
        ExperienceEntity entity = new ExperienceEntity();
        entity.setSkillSons(new ArrayList<>());
        ExperienceRequest request = new ExperienceRequest();
        request.setSkillSonIds(Collections.singletonList(1L));
        when(experienceMapper.toExperienceEntity(request)).thenReturn(entity);
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new SkillSonEntity()));
        when(experienceRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = experienceService.experiencePost(request);

        assertNotNull(response);
    }

    @Test
    void experienceIdDelete() {
        ExperienceEntity entity = new ExperienceEntity();
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(experienceRepository.save(any())).thenReturn(entity);

        experienceService.experienceIdDelete(1L);
    }

    @Test
    void getEntityById_notFound() {
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("experience.not.found.byid"));

        assertThrows(RuntimeException.class, () -> experienceService.experienceIdGet(1L));
    }

    @Test
    void getSkillSons_notFound() {
        ExperienceRequest request = new ExperienceRequest();
        request.setSkillSonIds(Collections.singletonList(1L));
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("skill.not.found"));

        assertThrows(RuntimeException.class, () -> experienceService.experiencePost(request));
    }
}
