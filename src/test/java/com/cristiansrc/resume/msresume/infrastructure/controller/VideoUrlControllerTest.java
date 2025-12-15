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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
    void videoUrlIdDelete() {
        doNothing().when(videoUrlService).videoUrlIdDelete(any());
        ResponseEntity<Void> response = videoUrlController.videoUrlIdDelete(1L);
        assertNotNull(response);
    }

    @Test
    void videoUrlIdGet() {
        when(videoUrlService.videoUrlIdGet(1L)).thenReturn(new VideoUrlResponse());
        ResponseEntity<VideoUrlResponse> response = videoUrlController.videoUrlIdGet(1L);
        assertNotNull(response);
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
