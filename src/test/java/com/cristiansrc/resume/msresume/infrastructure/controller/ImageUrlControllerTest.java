package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IImageUrlService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ImageUrlControllerTest {

    @Mock
    private IImageUrlService imageUrlService;

    @InjectMocks
    private ImageUrlController imageUrlController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void imageUrlGet() {
        ImageUrlResponse resp = new ImageUrlResponse();
        resp.setUrl("http://test.com/test.txt");
        when(imageUrlService.imageUrlGet()).thenReturn(Collections.singletonList(resp));
        ResponseEntity<List<ImageUrlResponse>> response = imageUrlController.imageUrlGet();
        assertNotNull(response);
        assertEquals("http://test.com/test.txt", response.getBody().get(0).getUrl());
    }

    @Test
    void imageUrlIdDelete() {
        doNothing().when(imageUrlService).imageUrlIdDelete(any());
        ResponseEntity<Void> response = imageUrlController.imageUrlIdDelete(1L);
        assertNotNull(response);
    }

    @Test
    void imageUrlIdGet() {
        ImageUrlResponse resp = new ImageUrlResponse();
        resp.setUrl("http://test.com/test.txt");
        when(imageUrlService.imageUrlIdGet(1L)).thenReturn(resp);
        ResponseEntity<ImageUrlResponse> response = imageUrlController.imageUrlIdGet(1L);
        assertNotNull(response);
        assertEquals("http://test.com/test.txt", response.getBody().getUrl());
    }

    @Test
    void imageUrlPost() {
        ImageUrlPost201Response serviceResponse = new ImageUrlPost201Response();
        serviceResponse.setId(1L);
        when(imageUrlService.imageUrlPost(any())).thenReturn(serviceResponse);
        ResponseEntity<ImageUrlPost201Response> response = imageUrlController.imageUrlPost(new ImageUrlRequest());
        assertNotNull(response);
    }
}
