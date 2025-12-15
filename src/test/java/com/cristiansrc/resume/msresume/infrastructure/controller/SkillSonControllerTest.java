package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.ISkillSonService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;
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

class SkillSonControllerTest {

    @Mock
    private ISkillSonService skillSonService;

    @InjectMocks
    private SkillSonController skillSonController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void skillSonGet() {
        when(skillSonService.skillSonGet()).thenReturn(Collections.singletonList(new SkillSonResponse()));
        ResponseEntity<List<SkillSonResponse>> response = skillSonController.skillSonGet();
        assertNotNull(response);
    }

    @Test
    void skillSonIdDelete() {
        doNothing().when(skillSonService).skillSonIdDelete(any());
        ResponseEntity<Void> response = skillSonController.skillSonIdDelete(1L);
        assertNotNull(response);
    }

    @Test
    void skillSonIdGet() {
        when(skillSonService.skillSonIdGet(1L)).thenReturn(new SkillSonResponse());
        ResponseEntity<SkillSonResponse> response = skillSonController.skillSonIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void skillSonIdPut() {
        doNothing().when(skillSonService).skillSonIdPut(any(), any());
        ResponseEntity<Void> response = skillSonController.skillSonIdPut(1L, new SkillSonRequest());
        assertNotNull(response);
    }

    @Test
    void skillSonPost() {
        ImageUrlPost201Response serviceResponse = new ImageUrlPost201Response();
        serviceResponse.setId(1L);
        when(skillSonService.skillSonPost(any())).thenReturn(serviceResponse);
        ResponseEntity<ImageUrlPost201Response> response = skillSonController.skillSonPost(new SkillSonRequest());
        assertNotNull(response);
    }
}
