package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBasicDataRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBasicDataMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BasicDataEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BasicDataServiceTest {

    @Mock
    private IBasicDataRepository basicDataRepository;

    @Mock
    private IBasicDataMapper basicDataMapper;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private BasicDataService basicDataService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void basicDataIdGet_success() {
        BasicDataEntity entity = new BasicDataEntity();
        BasicDataResponse response = new BasicDataResponse();
        response.setId(1L);

        when(basicDataRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(basicDataMapper.toBasicDataResponse(entity)).thenReturn(response);

        BasicDataResponse result = basicDataService.basicDataIdGet(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void basicDataIdGet_notFound() {
        when(basicDataRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("basic.data.not.found"));

        assertThrows(RuntimeException.class, () -> basicDataService.basicDataIdGet(1L));
    }

    @Test
    void basicDataIdPut_success() {
        BasicDataEntity entity = new BasicDataEntity();
        BasicDataRequest request = new BasicDataRequest();

        when(basicDataRepository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(basicDataMapper).updateBasicDataEntityFromBasicDataRequest(any(), any());
        when(basicDataRepository.save(any())).thenReturn(entity);

        basicDataService.basicDataIdPut(1L, request);

        verify(basicDataRepository).save(entity);
    }

    @Test
    void basicDataIdPut_notFound() {
        BasicDataRequest request = new BasicDataRequest();

        when(basicDataRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("basic.data.not.found"));

        assertThrows(RuntimeException.class, () -> basicDataService.basicDataIdPut(1L, request));
    }
}
