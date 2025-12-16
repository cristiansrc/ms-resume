package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBlogRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBlogRequestMapper;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBlogResponseMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class BlogServiceTest {

    @Mock
    private IBlogRepository blogRepository;

    @Mock
    private IBlogResponseMapper blogResponseMapper;

    @Mock
    private MessageResolver messageResolver;

    @Mock
    private IBlogRequestMapper blogRequestMapper;

    @InjectMocks
    private BlogService blogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void blogGet() {
        Page<BlogEntity> page = new PageImpl<>(Collections.singletonList(new BlogEntity()));
        when(blogRepository.findByTitleContainingIgnoreCaseAndSort(any(), any(PageRequest.class))).thenReturn(page);
        when(blogResponseMapper.toPageResponse(page)).thenReturn(new BlogPageResponse());

        BlogPageResponse response = blogService.blogGet(0, 10, "DESC");

        assertNotNull(response);
    }

    @Test
    void blogIdDelete() {
        BlogEntity entity = new BlogEntity();
        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(blogRepository.save(any())).thenReturn(entity);

        blogService.blogIdDelete(1L);
    }

    @Test
    void blogIdGet() {
        BlogEntity entity = new BlogEntity();
        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(blogResponseMapper.toResponse(entity)).thenReturn(new BlogResponse());

        BlogResponse response = blogService.blogIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void blogIdPut() {
        BlogEntity entity = new BlogEntity();
        BlogRequest request = new BlogRequest();
        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        doNothing().when(blogRequestMapper).updateEntityFromBlogRequest(any(), any());
        when(blogRepository.save(any())).thenReturn(entity);

        blogService.blogIdPut(1L, request);
    }

    @Test
    void blogPost() {
        BlogEntity entity = new BlogEntity();
        BlogRequest request = new BlogRequest();
        when(blogRequestMapper.toEntity(request)).thenReturn(entity);
        when(blogRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = blogService.blogPost(request);

        assertNotNull(response);
    }

    @Test
    void getEntityById_notFound() {
        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("blog.post.not.found"));

        assertThrows(RuntimeException.class, () -> blogService.blogIdGet(1L));
    }
}
