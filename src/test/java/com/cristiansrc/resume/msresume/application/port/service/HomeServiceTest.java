package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IHomeMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class HomeServiceTest {

    @Mock
    private IHomeRepository homeRepository;

    @Mock
    private IHomeMapper homeMapper;

    @Mock
    private IImageUrlRepository imageUrlRepository;

    @Mock
    private MessageResolver messageResolver;

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
        when(homeMapper.toResponse(entity)).thenReturn(new HomeResponse());

        HomeResponse response = homeService.homeIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void homeIdPut() {
        HomeEntity entity = new HomeEntity();
        entity.setId(1L);
        HomeRequest request = new HomeRequest();
        when(homeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new ImageUrlEntity()));
        doNothing().when(homeMapper).updateEntityFromRequest(any(), any());
        when(homeRepository.save(any())).thenReturn(entity);

        homeService.homeIdPut(1L, request);
    }

    @Test
    void homeIdPut_imageNotFound_throws() {
        HomeEntity entity = new HomeEntity();
        entity.setId(1L);
        HomeRequest request = new HomeRequest();
        when(homeRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("image.home.not.found"));

        assertThrows(RuntimeException.class, () -> homeService.homeIdPut(1L, request));
    }

    @Test
    void getEntityById_notFound() {
        when(homeRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("home.not.found.byid"));

        assertThrows(RuntimeException.class, () -> homeService.homeIdGet(1L));
    }
}
