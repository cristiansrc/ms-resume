package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ISkillSonRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ISkillSonMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class SkillSonServiceTest {

    @Mock
    private ISkillSonRepository skillSonRepository;

    @Mock
    private ISkillSonMapper skillSonMapper;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private SkillSonService skillSonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void skillSonGet() {
        when(skillSonRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(new SkillSonEntity()));
        when(skillSonMapper.toRequestList(any())).thenReturn(Collections.singletonList(new SkillSonResponse()));

        List<SkillSonResponse> response = skillSonService.skillSonGet();

        assertNotNull(response);
    }

    @Test
    void skillSonIdDelete() {
        SkillSonEntity entity = new SkillSonEntity();
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(skillSonRepository.save(any())).thenReturn(entity);

        skillSonService.skillSonIdDelete(1L);
    }

    @Test
    void skillSonIdGet() {
        SkillSonEntity entity = new SkillSonEntity();
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(skillSonMapper.toRequest(entity)).thenReturn(new SkillSonResponse());

        SkillSonResponse response = skillSonService.skillSonIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void skillSonIdPut() {
        SkillSonEntity entity = new SkillSonEntity();
        SkillSonRequest request = new SkillSonRequest();
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        doNothing().when(skillSonMapper).updateEntity(any(), any());
        when(skillSonRepository.save(any())).thenReturn(entity);

        skillSonService.skillSonIdPut(1L, request);
    }

    @Test
    void skillSonPost() {
        SkillSonEntity entity = new SkillSonEntity();
        SkillSonRequest request = new SkillSonRequest();
        when(skillSonMapper.toEntity(request)).thenReturn(entity);
        when(skillSonRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = skillSonService.skillSonPost(request);

        assertNotNull(response);
    }

    @Test
    void getEntityById_notFound() {
        when(skillSonRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("skill.son.not.found"));

        assertThrows(RuntimeException.class, () -> skillSonService.skillSonIdGet(1L));
    }
}
