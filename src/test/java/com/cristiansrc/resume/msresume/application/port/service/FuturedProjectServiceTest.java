package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IExperienceRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IFuturedProjectRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IFuturedProjectMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.FuturedProjectEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
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

class FuturedProjectServiceTest {

    @Mock
    private IFuturedProjectRepository futuredProjectRepository;

    @Mock
    private IFuturedProjectMapper futuredProjectMapper;

    @Mock
    private IExperienceRepository experienceRepository;

    @Mock
    private IImageUrlRepository imageUrlRepository;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private FuturedProjectService futuredProjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void futuredProjectGet() {
        when(futuredProjectRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(new FuturedProjectEntity()));
        when(futuredProjectMapper.toFuturedProjectResponse(any())).thenReturn(new FuturedProjectResponse());

        List<FuturedProjectResponse> response = futuredProjectService.futuredProjectGet();

        assertNotNull(response);
    }

    @Test
    void futuredProjectIdDelete() {
        FuturedProjectEntity entity = new FuturedProjectEntity();
        when(futuredProjectRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(futuredProjectRepository.save(any())).thenReturn(entity);

        futuredProjectService.futuredProjectIdDelete(1L);
    }

    @Test
    void futuredProjectIdGet() {
        FuturedProjectEntity entity = new FuturedProjectEntity();
        when(futuredProjectRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(futuredProjectMapper.toFuturedProjectResponse(entity)).thenReturn(new FuturedProjectResponse());

        FuturedProjectResponse response = futuredProjectService.futuredProjectIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void futuredProjectIdPut() {
        FuturedProjectEntity entity = new FuturedProjectEntity();
        FuturedProjectRequest request = new FuturedProjectRequest();
        request.setExperienceId(1L);
        request.setImageUrlId(1L);
        request.setImageListUrlId(1L);
        when(futuredProjectRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new ExperienceEntity()));
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new ImageUrlEntity()));
        doNothing().when(futuredProjectMapper).updateFuturedProjectEntityFromRequest(any(), any());
        when(futuredProjectRepository.save(any())).thenReturn(entity);

        futuredProjectService.futuredProjectIdPut(1L, request);
    }

    @Test
    void futuredProjectPost() {
        FuturedProjectEntity entity = new FuturedProjectEntity();
        entity.setId(1L);
        FuturedProjectRequest request = new FuturedProjectRequest();
        request.setExperienceId(1L);
        request.setImageUrlId(1L);
        request.setImageListUrlId(1L);
        when(futuredProjectMapper.toFuturedProjectEntity(request)).thenReturn(entity);
        when(experienceRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new ExperienceEntity()));
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new ImageUrlEntity()));
        when(futuredProjectRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = futuredProjectService.futuredProjectPost(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getEntityById_notFound() {
        when(futuredProjectRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("futured.project.not.found"));

        assertThrows(RuntimeException.class, () -> futuredProjectService.futuredProjectIdGet(1L));
    }
}
