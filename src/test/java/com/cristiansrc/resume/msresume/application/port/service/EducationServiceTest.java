package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IEducationRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IEducationMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.EducationEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.AfterEach;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EducationServiceTest {

    @Mock
    private IEducationRepository educationRepository;

    @Mock
    private IEducationMapper educationMapper;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private EducationService educationService;

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
    void educationGet() {
        when(educationRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(new EducationEntity()));
        when(educationMapper.toEducationResponseList(any())).thenReturn(Collections.singletonList(new EducationResponse()));

        List<EducationResponse> response = educationService.educationGet();

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    void educationGet_empty() {
        when(educationRepository.findAllByDeletedFalse()).thenReturn(Collections.emptyList());
        when(educationMapper.toEducationResponseList(any())).thenReturn(Collections.emptyList());

        List<EducationResponse> response = educationService.educationGet();

        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void educationIdGet() {
        EducationEntity entity = new EducationEntity();
        when(educationRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(educationMapper.toEducationResponse(entity)).thenReturn(new EducationResponse());

        EducationResponse response = educationService.educationIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void educationIdPut() {
        EducationEntity entity = new EducationEntity();
        EducationRequest request = new EducationRequest();
        
        when(educationRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        doNothing().when(educationMapper).updateEducationEntityFromRequest(any(), any());
        when(educationRepository.save(any())).thenReturn(entity);

        educationService.educationIdPut(1L, request);
        
        verify(educationRepository).save(entity);
    }

    @Test
    void educationPost() {
        EducationEntity entity = new EducationEntity();
        EducationRequest request = new EducationRequest();
        
        when(educationMapper.toEducationEntity(request)).thenReturn(entity);
        when(educationRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = educationService.educationPost(request);

        assertNotNull(response);
        verify(educationRepository).save(entity);
    }

    @Test
    void educationIdDelete() {
        EducationEntity entity = new EducationEntity();
        when(educationRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(educationRepository.save(any())).thenReturn(entity);

        educationService.educationIdDelete(1L);
        
        assertTrue(entity.getDeleted());
        verify(educationRepository).save(entity);
    }

    @Test
    void educationIdDelete_notFound() {
        when(educationRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("education.not.found.byid"));

        assertThrows(RuntimeException.class, () -> educationService.educationIdDelete(1L));
    }

    @Test
    void getEntityById_notFound() {
        when(educationRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("education.not.found.byid"));

        assertThrows(RuntimeException.class, () -> educationService.educationIdGet(1L));
    }
}
