package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IExperienceService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
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

class ExperienceControllerTest {

    @Mock
    private IExperienceService experienceService;

    @InjectMocks
    private ExperienceController experienceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void experienceGet() {
        when(experienceService.experienceGet()).thenReturn(Collections.singletonList(new ExperienceResponse()));
        ResponseEntity<List<ExperienceResponse>> response = experienceController.experienceGet();
        assertNotNull(response);
    }

    @Test
    void experienceIdGet() {
        when(experienceService.experienceIdGet(1L)).thenReturn(new ExperienceResponse());
        ResponseEntity<ExperienceResponse> response = experienceController.experienceIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void experienceIdPut() {
        doNothing().when(experienceService).experienceIdPut(any(), any());
        ResponseEntity<Void> response = experienceController.experienceIdPut(1L, new ExperienceRequest());
        assertNotNull(response);
    }

    @Test
    void experiencePost() {
        ImageUrlPost201Response serviceResponse = new ImageUrlPost201Response();
        serviceResponse.setId(1L);
        when(experienceService.experiencePost(any())).thenReturn(serviceResponse);
        ResponseEntity<ImageUrlPost201Response> response = experienceController.experiencePost(new ExperienceRequest());
        assertNotNull(response);
    }

    @Test
    void experienceIdDelete() {
        doNothing().when(experienceService).experienceIdDelete(any());
        ResponseEntity<Void> response = experienceController.experienceIdDelete(1L);
        assertNotNull(response);
    }
}
