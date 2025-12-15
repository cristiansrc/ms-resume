package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.ISkillTypeService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeResponse;
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

class SkillTypeControllerTest {

    @Mock
    private ISkillTypeService skillTypeService;

    @InjectMocks
    private SkillTypeController skillTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void skillTypeGet() {
        when(skillTypeService.skillTypeGet()).thenReturn(Collections.singletonList(new SkillTypeResponse()));
        ResponseEntity<List<SkillTypeResponse>> response = skillTypeController.skillTypeGet();
        assertNotNull(response);
    }

    @Test
    void skillTypeIdDelete() {
        doNothing().when(skillTypeService).skillTypeIdDelete(any());
        ResponseEntity<Void> response = skillTypeController.skillTypeIdDelete(1L);
        assertNotNull(response);
    }

    @Test
    void skillTypeIdGet() {
        when(skillTypeService.skillTypeIdGet(1L)).thenReturn(new SkillTypeResponse());
        ResponseEntity<SkillTypeResponse> response = skillTypeController.skillTypeIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void skillTypeIdPut() {
        doNothing().when(skillTypeService).skillTypeIdPut(any(), any());
        ResponseEntity<Void> response = skillTypeController.skillTypeIdPut(1L, new SkillTypeRequest());
        assertNotNull(response);
    }

    @Test
    void skillTypePost() {
        ImageUrlPost201Response serviceResponse = new ImageUrlPost201Response();
        serviceResponse.setId(1L);
        when(skillTypeService.skillTypePost(any())).thenReturn(serviceResponse);
        ResponseEntity<ImageUrlPost201Response> response = skillTypeController.skillTypePost(new SkillTypeRequest());
        assertNotNull(response);
    }
}
