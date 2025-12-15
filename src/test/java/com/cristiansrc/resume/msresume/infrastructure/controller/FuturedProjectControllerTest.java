package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IFuturedProjectService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
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

class FuturedProjectControllerTest {

    @Mock
    private IFuturedProjectService futuredProjectService;

    @InjectMocks
    private FuturedProjectController futuredProjectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void futuredProjectGet() {
        when(futuredProjectService.futuredProjectGet()).thenReturn(Collections.singletonList(new FuturedProjectResponse()));
        ResponseEntity<List<FuturedProjectResponse>> response = futuredProjectController.futuredProjectGet();
        assertNotNull(response);
    }

    @Test
    void futuredProjectIdDelete() {
        doNothing().when(futuredProjectService).futuredProjectIdDelete(any());
        ResponseEntity<Void> response = futuredProjectController.futuredProjectIdDelete(1L);
        assertNotNull(response);
    }

    @Test
    void futuredProjectIdGet() {
        when(futuredProjectService.futuredProjectIdGet(1L)).thenReturn(new FuturedProjectResponse());
        ResponseEntity<FuturedProjectResponse> response = futuredProjectController.futuredProjectIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void futuredProjectIdPut() {
        doNothing().when(futuredProjectService).futuredProjectIdPut(any(), any());
        ResponseEntity<Void> response = futuredProjectController.futuredProjectIdPut(1L, new FuturedProjectRequest());
        assertNotNull(response);
    }

    @Test
    void futuredProjectPost() {
        when(futuredProjectService.futuredProjectPost(any())).thenReturn(new ImageUrlPost201Response());
        ResponseEntity<ImageUrlPost201Response> response = futuredProjectController.futuredProjectPost(new FuturedProjectRequest());
        assertNotNull(response);
    }
}
