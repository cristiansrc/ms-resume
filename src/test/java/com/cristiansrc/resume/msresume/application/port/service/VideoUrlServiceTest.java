package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IVideoUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IVideoUrlMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.VideoUrlEntity;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VideoUrlServiceTest {

    @Mock
    private IVideoUrlRepository videoUrlRepository;

    @Mock
    private IVideoUrlMapper videoUrlMapper;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private VideoUrlService videoUrlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void videoUrlGet_shouldReturnVideoUrlList() {
        VideoUrlEntity entity = new VideoUrlEntity();
        when(videoUrlRepository.findAllNotDeleted()).thenReturn(Collections.singletonList(entity));
        VideoUrlResponse response = new VideoUrlResponse();
        when(videoUrlMapper.videoUrlToVideoUrlResponseList(anyList())).thenReturn(Collections.singletonList(response));

        List<VideoUrlResponse> result = videoUrlService.videoUrlGet();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(videoUrlRepository).findAllNotDeleted();
        verify(videoUrlMapper).videoUrlToVideoUrlResponseList(anyList());
    }

    @Test
    void videoUrlGet_shouldReturnEmptyList() {
        when(videoUrlRepository.findAllNotDeleted()).thenReturn(Collections.emptyList());
        when(videoUrlMapper.videoUrlToVideoUrlResponseList(anyList())).thenReturn(Collections.emptyList());

        List<VideoUrlResponse> result = videoUrlService.videoUrlGet();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(videoUrlRepository).findAllNotDeleted();
        verify(videoUrlMapper).videoUrlToVideoUrlResponseList(anyList());
    }

    @Test
    void videoUrlIdDelete_shouldDeleteVideoUrl() {
        Long id = 1L;
        VideoUrlEntity entity = new VideoUrlEntity();
        entity.setDeleted(false);
        when(videoUrlRepository.findByIdAndNotDeleted(id)).thenReturn(Optional.of(entity));

        videoUrlService.videoUrlIdDelete(id);

        assertTrue(entity.getDeleted());
        verify(videoUrlRepository).findByIdAndNotDeleted(id);
        verify(videoUrlRepository).save(entity);
    }

    @Test
    void videoUrlIdDelete_shouldThrowNotFound() {
        Long id = 1L;
        when(videoUrlRepository.findByIdAndNotDeleted(id)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("Not Found"));

        assertThrows(RuntimeException.class, () -> videoUrlService.videoUrlIdDelete(id));
        verify(videoUrlRepository).findByIdAndNotDeleted(id);
    }

    @Test
    void videoUrlIdGet_shouldReturnVideoUrl() {
        Long id = 1L;
        VideoUrlEntity entity = new VideoUrlEntity();
        when(videoUrlRepository.findByIdAndNotDeleted(id)).thenReturn(Optional.of(entity));
        VideoUrlResponse response = new VideoUrlResponse();
        when(videoUrlMapper.videoUrlToVideoUrlResponse(entity)).thenReturn(response);

        VideoUrlResponse result = videoUrlService.videoUrlIdGet(id);

        assertNotNull(result);
        verify(videoUrlRepository).findByIdAndNotDeleted(id);
        verify(videoUrlMapper).videoUrlToVideoUrlResponse(entity);
    }

    @Test
    void videoUrlIdGet_shouldThrowNotFound() {
        Long id = 1L;
        when(videoUrlRepository.findByIdAndNotDeleted(id)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("Not Found"));

        assertThrows(RuntimeException.class, () -> videoUrlService.videoUrlIdGet(id));
        verify(videoUrlRepository).findByIdAndNotDeleted(id);
    }

    @Test
    void videoUrlPost_shouldCreateVideoUrl() {
        VideoUrlRequest request = new VideoUrlRequest();
        VideoUrlEntity entity = new VideoUrlEntity();
        when(videoUrlMapper.toEntity(request)).thenReturn(entity);
        VideoUrlEntity savedEntity = new VideoUrlEntity();
        when(videoUrlRepository.save(entity)).thenReturn(savedEntity);

        ImageUrlPost201Response response = videoUrlService.videoUrlPost(request);

        assertNotNull(response);
        verify(videoUrlMapper).toEntity(request);
        verify(videoUrlRepository).save(entity);
    }
}
