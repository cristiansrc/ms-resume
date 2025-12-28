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
import org.junit.jupiter.api.AfterEach;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void experienceGet() {
        when(experienceRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(new ExperienceEntity()));
        when(experienceMapper.toExperienceResponseList(any())).thenReturn(Collections.singletonList(new ExperienceResponse()));

        List<ExperienceResponse> response = experienceService.experienceGet();

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    void experienceGet_empty() {
        when(experienceRepository.findAllByDeletedFalse()).thenReturn(Collections.emptyList());
        when(experienceMapper.toExperienceResponseList(any())).thenReturn(Collections.emptyList());

        List<ExperienceResponse> response = experienceService.experienceGet();

        assertNotNull(response);
        assertEquals(0, response.size());
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
        entity.setExperienceSkills(new ArrayList<>());
        ExperienceRequest request = new ExperienceRequest();
        request.setSkillSonIds(Collections.singletonList(1L));
        
        SkillSonEntity skillSon = new SkillSonEntity();
        
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(skillSon));
        doNothing().when(experienceMapper).updateExperienceEntityFromRequest(any(), any());
        when(experienceRepository.save(any())).thenReturn(entity);

        experienceService.experienceIdPut(1L, request);
        
        verify(experienceRepository).saveAndFlush(entity);
        verify(experienceRepository).save(entity);
        assertEquals(1, entity.getExperienceSkills().size());
    }

    @Test
    void experienceIdPut_withNullExperienceSkills() {
        ExperienceEntity entity = new ExperienceEntity();
        entity.setExperienceSkills(null);
        ExperienceRequest request = new ExperienceRequest();
        request.setSkillSonIds(Collections.singletonList(1L));
        
        SkillSonEntity skillSon = new SkillSonEntity();

        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(skillSon));
        doNothing().when(experienceMapper).updateExperienceEntityFromRequest(any(), any());
        when(experienceRepository.save(any())).thenReturn(entity);

        experienceService.experienceIdPut(1L, request);

        assertNotNull(entity.getExperienceSkills());
        assertEquals(1, entity.getExperienceSkills().size());
    }

    @Test
    void experienceIdPut_withNullSkillSonIds() {
        ExperienceEntity entity = new ExperienceEntity();
        entity.setExperienceSkills(new ArrayList<>());
        ExperienceRequest request = new ExperienceRequest();
        request.setSkillSonIds(null);
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        doNothing().when(experienceMapper).updateExperienceEntityFromRequest(any(), any());
        when(experienceRepository.save(any())).thenReturn(entity);

        experienceService.experienceIdPut(1L, request);
        
        assertEquals(0, entity.getExperienceSkills().size());
    }

    @Test
    void experiencePost() {
        ExperienceEntity entity = new ExperienceEntity();
        entity.setExperienceSkills(new ArrayList<>());
        ExperienceRequest request = new ExperienceRequest();
        request.setSkillSonIds(Collections.singletonList(1L));
        
        SkillSonEntity skillSon = new SkillSonEntity();
        
        when(experienceMapper.toExperienceEntity(request)).thenReturn(entity);
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(skillSon));
        when(experienceRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = experienceService.experiencePost(request);

        assertNotNull(response);
        verify(experienceRepository).save(entity);
        assertEquals(1, entity.getExperienceSkills().size());
    }

    @Test
    void experiencePost_withNullExperienceSkills() {
        ExperienceEntity entity = new ExperienceEntity();
        entity.setExperienceSkills(null);
        ExperienceRequest request = new ExperienceRequest();
        request.setSkillSonIds(Collections.singletonList(1L));
        
        SkillSonEntity skillSon = new SkillSonEntity();
        
        when(experienceMapper.toExperienceEntity(request)).thenReturn(entity);
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(skillSon));
        when(experienceRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = experienceService.experiencePost(request);

        assertNotNull(response);
        assertNotNull(entity.getExperienceSkills());
        assertEquals(1, entity.getExperienceSkills().size());
    }

    @Test
    void experiencePost_withNullSkillSonIds() {
        ExperienceEntity entity = new ExperienceEntity();
        entity.setExperienceSkills(new ArrayList<>());
        ExperienceRequest request = new ExperienceRequest();
        request.setSkillSonIds(null);
        when(experienceMapper.toExperienceEntity(request)).thenReturn(entity);
        when(experienceRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = experienceService.experiencePost(request);

        assertNotNull(response);
        assertEquals(0, entity.getExperienceSkills().size());
    }

    @Test
    void experienceIdDelete() {
        ExperienceEntity entity = new ExperienceEntity();
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(experienceRepository.save(any())).thenReturn(entity);

        experienceService.experienceIdDelete(1L);
        
        assertTrue(entity.getDeleted());
        verify(experienceRepository).save(entity);
    }

    @Test
    void experienceIdDelete_notFound() {
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("experience.not.found.byid"));

        assertThrows(RuntimeException.class, () -> experienceService.experienceIdDelete(1L));
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
