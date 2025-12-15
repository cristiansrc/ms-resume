package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IBasicDataService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
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

class BasicDataControllerTest {

    @Mock
    private IBasicDataService basicDataService;

    @InjectMocks
    private BasicDataController basicDataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void basicDataIdGet() {
        when(basicDataService.basicDataIdGet(1L)).thenReturn(new BasicDataResponse());
        ResponseEntity<BasicDataResponse> response = basicDataController.basicDataIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void basicDataIdPut() {
        doNothing().when(basicDataService).basicDataIdPut(any(), any());
        ResponseEntity<Void> response = basicDataController.basicDataIdPut(1L, new BasicDataRequest());
        assertNotNull(response);
    }
}
