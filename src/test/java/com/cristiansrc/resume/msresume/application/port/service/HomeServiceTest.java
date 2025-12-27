package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ILabelRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IHomeMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.LabelEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HomeServiceTest {

    @Mock
    private IHomeRepository homeRepository;

    @Mock
    private IHomeMapper homeMapper;

    @Mock
    private IImageUrlRepository imageUrlRepository;

    @Mock
    private ILabelRepository labelRepository;

    @Mock
    private MessageResolver messageResolver;

    @Mock
    private IS3Service s3Service;

    @InjectMocks
    private HomeService homeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void homeIdGet() {
        HomeEntity entity = new HomeEntity();
        when(homeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(homeMapper.toResponse(eq(entity), any())).thenReturn(new HomeResponse());

        HomeResponse response = homeService.homeIdGet(1L);

        assertNotNull(response);
        verify(homeMapper).toResponse(entity, s3Service);
    }

    @Test
    void homeIdPut() {
        HomeEntity entity = new HomeEntity();
        entity.setHomeLabelRelations(new ArrayList<>()); // Inicializar lista para evitar NPE en el servicio
        HomeRequest request = new HomeRequest();
        request.setImageUrlId(1L);
        request.setLabelIds(Arrays.asList(1L, 2L));

        when(homeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new ImageUrlEntity()));
        
        LabelEntity label1 = new LabelEntity();
        LabelEntity label2 = new LabelEntity();
        when(labelRepository.findAllById(any())).thenReturn(Arrays.asList(label1, label2));
        
        doNothing().when(homeMapper).updateEntityFromRequest(any(), any());
        when(homeRepository.save(any())).thenReturn(entity);

        homeService.homeIdPut(1L, request);
        
        verify(homeRepository).save(entity);
    }

    @Test
    void homeIdPut_imageNotFound_throws() {
        HomeEntity entity = new HomeEntity();
        HomeRequest request = new HomeRequest();
        request.setImageUrlId(1L);
        
        when(homeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(eq("image.home.not.found"), any())).thenThrow(new RuntimeException("image.home.not.found"));

        assertThrows(RuntimeException.class, () -> homeService.homeIdPut(1L, request));
    }

    @Test
    void homeIdPut_labelNotFound_throws() {
        HomeEntity entity = new HomeEntity();
        entity.setHomeLabelRelations(new ArrayList<>());
        HomeRequest request = new HomeRequest();
        request.setLabelIds(Collections.singletonList(99L));

        when(homeRepository.findById(1L)).thenReturn(Optional.of(entity));
        // Simular que no se encuentra el label
        when(labelRepository.findAllById(any())).thenReturn(Collections.emptyList());
        when(messageResolver.notFound(eq("label.not.found"), any())).thenThrow(new RuntimeException("label.not.found"));

        assertThrows(RuntimeException.class, () -> homeService.homeIdPut(1L, request));
    }

    @Test
    void getEntityById_notFound() {
        when(homeRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("home.not.found.byid"));

        assertThrows(RuntimeException.class, () -> homeService.homeIdGet(1L));
    }
}
