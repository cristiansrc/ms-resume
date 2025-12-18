package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IVideoUrlService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class VideoUrlControllerTest {

    @Mock
    private IVideoUrlService videoUrlService;

    @InjectMocks
    private VideoUrlController videoUrlController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void videoUrlGet() {
        when(videoUrlService.videoUrlGet()).thenReturn(Collections.singletonList(new VideoUrlResponse()));
        ResponseEntity<List<VideoUrlResponse>> response = videoUrlController.videoUrlGet();
        assertNotNull(response);
    }

    @Test
    void videoUrlGet_empty() {
        when(videoUrlService.videoUrlGet()).thenReturn(Collections.emptyList());
        ResponseEntity<List<VideoUrlResponse>> response = videoUrlController.videoUrlGet();
        assertNotNull(response);
        assertEquals(0, response.getBody().size());
    }

    @Test
    void videoUrlIdDelete() {
        doNothing().when(videoUrlService).videoUrlIdDelete(any());
        ResponseEntity<Void> response = videoUrlController.videoUrlIdDelete(1L);
        assertNotNull(response);
    }

    @Test
    void videoUrlIdDelete_notFound() {
        doThrow(new RuntimeException("not found")).when(videoUrlService).videoUrlIdDelete(1L);
        assertThrows(RuntimeException.class, () -> videoUrlController.videoUrlIdDelete(1L));
    }

    @Test
    void videoUrlIdGet() {
        when(videoUrlService.videoUrlIdGet(1L)).thenReturn(new VideoUrlResponse());
        ResponseEntity<VideoUrlResponse> response = videoUrlController.videoUrlIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void videoUrlIdGet_notFound() {
        when(videoUrlService.videoUrlIdGet(1L)).thenThrow(new RuntimeException("not found"));
        assertThrows(RuntimeException.class, () -> videoUrlController.videoUrlIdGet(1L));
    }

    @Test
    void videoUrlPost() {
        ImageUrlPost201Response serviceResponse = new ImageUrlPost201Response();
        serviceResponse.setId(1L);
        when(videoUrlService.videoUrlPost(any())).thenReturn(serviceResponse);
        ResponseEntity<ImageUrlPost201Response> response = videoUrlController.videoUrlPost(new VideoUrlRequest());
        assertNotNull(response);
    }
}
