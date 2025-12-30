package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IEducationService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import org.junit.jupiter.api.AfterEach;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class EducationControllerTest {

    @Mock
    private IEducationService educationService;

    @InjectMocks
    private EducationController educationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void educationGet() {
        when(educationService.educationGet()).thenReturn(Collections.singletonList(new EducationResponse()));
        ResponseEntity<List<EducationResponse>> response = educationController.educationGet();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void educationIdGet() {
        when(educationService.educationIdGet(1L)).thenReturn(new EducationResponse());
        ResponseEntity<EducationResponse> response = educationController.educationIdGet(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void educationIdPut() {
        doNothing().when(educationService).educationIdPut(eq(1L), any(EducationRequest.class));
        ResponseEntity<Void> response = educationController.educationIdPut(1L, new EducationRequest());
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void educationPost() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ImageUrlPost201Response mockResponse = new ImageUrlPost201Response();
        mockResponse.setId(1L);
        when(educationService.educationPost(any(EducationRequest.class))).thenReturn(mockResponse);

        ResponseEntity<ImageUrlPost201Response> response = educationController.educationPost(new EducationRequest());
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void educationIdDelete() {
        doNothing().when(educationService).educationIdDelete(1L);
        ResponseEntity<Void> response = educationController.educationIdDelete(1L);
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }
}
