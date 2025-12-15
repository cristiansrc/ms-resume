package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ILabelRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ILabelMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.LabelEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class LabelServiceTest {

    @Mock
    private ILabelRepository labelRepository;

    @Mock
    private ILabelMapper labelMapper;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private LabelService labelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void labelGet() {
        when(labelRepository.findAll()).thenReturn(Collections.singletonList(new LabelEntity()));
        when(labelMapper.labelToLabelResponse(any(List.class))).thenReturn(Collections.singletonList(new LabelResponse()));

        List<LabelResponse> response = labelService.labelGet();

        assertNotNull(response);
    }

    @Test
    void labelIdDelete() {
        LabelEntity entity = new LabelEntity();
        when(labelRepository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(labelRepository).delete(any());

        labelService.labelIdDelete(1L);
    }

    @Test
    void labelIdGet() {
        LabelEntity entity = new LabelEntity();
        when(labelRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(labelMapper.labelToLabelResponse(any(LabelEntity.class))).thenReturn(new LabelResponse());

        LabelResponse response = labelService.labelIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void labelPost() {
        LabelEntity entity = new LabelEntity();
        entity.setId(1L);
        LabelRequest request = new LabelRequest();
        when(labelMapper.labelToLabelEntity(request)).thenReturn(entity);
        when(labelRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = labelService.labelPost(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void entityById_notFound() {
        when(labelRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("label.not.found"));

        assertThrows(RuntimeException.class, () -> labelService.labelIdGet(1L));
    }
}
