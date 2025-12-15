package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.ILabelService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelResponse;
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

class LabelControllerTest {

    @Mock
    private ILabelService labelService;

    @InjectMocks
    private LabelController labelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void labelGet() {
        when(labelService.labelGet()).thenReturn(Collections.singletonList(new LabelResponse()));
        ResponseEntity<List<LabelResponse>> response = labelController.labelGet();
        assertNotNull(response);
    }

    @Test
    void labelIdDelete() {
        doNothing().when(labelService).labelIdDelete(any());
        ResponseEntity<Void> response = labelController.labelIdDelete(1L);
        assertNotNull(response);
    }

    @Test
    void labelIdGet() {
        when(labelService.labelIdGet(1L)).thenReturn(new LabelResponse());
        ResponseEntity<LabelResponse> response = labelController.labelIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void labelPost() {
        ImageUrlPost201Response serviceResponse = new ImageUrlPost201Response();
        serviceResponse.setId(1L);
        when(labelService.labelPost(any())).thenReturn(serviceResponse);
        ResponseEntity<ImageUrlPost201Response> response = labelController.labelPost(new LabelRequest());
        assertNotNull(response);
    }
}
