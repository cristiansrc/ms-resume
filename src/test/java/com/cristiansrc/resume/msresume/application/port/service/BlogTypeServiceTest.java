package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBlogTypeRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBlogTypeMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogTypeEntity;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BlogTypeServiceTest {

    @Mock
    private IBlogTypeRepository blogTypeRepository;

    @Mock
    private IBlogTypeMapper blogTypeMapper;

    @Mock
    private MessageResolver messageResolver;

    @InjectMocks
    private BlogTypeService blogTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void blogTypeGet() {
        when(blogTypeRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(new BlogTypeEntity()));
        when(blogTypeMapper.toResponseList(any())).thenReturn(Collections.singletonList(new BlogTypeResponse()));

        List<BlogTypeResponse> response = blogTypeService.blogTypeGet();

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    void blogTypeIdDelete() {
        BlogTypeEntity entity = new BlogTypeEntity();
        when(blogTypeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(blogTypeRepository.save(any())).thenReturn(entity);

        blogTypeService.blogTypeIdDelete(1L);

        verify(blogTypeRepository).save(entity);
    }

    @Test
    void blogTypeIdGet() {
        BlogTypeEntity entity = new BlogTypeEntity();
        when(blogTypeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(blogTypeMapper.toResponse(entity)).thenReturn(new BlogTypeResponse());

        BlogTypeResponse response = blogTypeService.blogTypeIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void blogTypeIdPut() {
        BlogTypeEntity entity = new BlogTypeEntity();
        BlogTypeRequest request = new BlogTypeRequest();
        when(blogTypeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        doNothing().when(blogTypeMapper).updateEntity(any(), any());
        when(blogTypeRepository.save(any())).thenReturn(entity);

        blogTypeService.blogTypeIdPut(1L, request);

        verify(blogTypeRepository).save(entity);
    }

    @Test
    void blogTypePost() {
        BlogTypeEntity entity = new BlogTypeEntity();
        BlogTypeRequest request = new BlogTypeRequest();
        when(blogTypeMapper.toEntity(request)).thenReturn(entity);
        when(blogTypeRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = blogTypeService.blogTypePost(request);

        assertNotNull(response);
    }

    @Test
    void getEntityById_notFound() {
        when(blogTypeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("blog.type.not.found"));

        assertThrows(RuntimeException.class, () -> blogTypeService.blogTypeIdGet(1L));
    }
}
