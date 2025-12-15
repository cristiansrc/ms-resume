package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.ISkillService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
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

class SkillControllerTest {

    @Mock
    private ISkillService skillService;

    @InjectMocks
    private SkillController skillController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void skillGet() {
        when(skillService.skillGet()).thenReturn(Collections.singletonList(new SkillResponse()));
        ResponseEntity<List<SkillResponse>> response = skillController.skillGet();
        assertNotNull(response);
    }

    @Test
    void skillIdDelete() {
        doNothing().when(skillService).skillIdDelete(any());
        ResponseEntity<Void> response = skillController.skillIdDelete(1L);
        assertNotNull(response);
    }

    @Test
    void skillIdGet() {
        when(skillService.skillIdGet(1L)).thenReturn(new SkillResponse());
        ResponseEntity<SkillResponse> response = skillController.skillIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void skillIdPut() {
        doNothing().when(skillService).skillIdPut(any(), any());
        ResponseEntity<Void> response = skillController.skillIdPut(1L, new SkillRequest());
        assertNotNull(response);
    }

    @Test
    void skillPost() {
        ImageUrlPost201Response serviceResponse = new ImageUrlPost201Response();
        serviceResponse.setId(1L);
        when(skillService.skillPost(any())).thenReturn(serviceResponse);
        ResponseEntity<ImageUrlPost201Response> response = skillController.skillPost(new SkillRequest());
        assertNotNull(response);
    }
}
