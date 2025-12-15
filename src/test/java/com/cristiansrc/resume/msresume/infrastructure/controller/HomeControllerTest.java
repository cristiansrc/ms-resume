package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IHomeService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class HomeControllerTest {

    @Mock
    private IHomeService homeService;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void homeIdGet() {
        when(homeService.homeIdGet(1L)).thenReturn(new HomeResponse());
        ResponseEntity<HomeResponse> response = homeController.homeIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void homeIdPut() {
        doNothing().when(homeService).homeIdPut(any(), any());
        ResponseEntity<Void> response = homeController.homeIdPut(1L, new HomeRequest());
        assertNotNull(response);
    }
}
