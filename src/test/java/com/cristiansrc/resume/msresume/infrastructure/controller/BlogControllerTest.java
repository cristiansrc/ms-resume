package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IBlogService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class BlogControllerTest {

    @Mock
    private IBlogService blogService;

    @InjectMocks
    private BlogController blogController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void blogGet() {
        when(blogService.blogGet(any(), any(), any())).thenReturn(new BlogPageResponse());
        ResponseEntity<BlogPageResponse> response = blogController.blogGet(0, 10, "DESC");
        assertNotNull(response);
    }

    @Test
    void blogIdDelete() {
        doNothing().when(blogService).blogIdDelete(any());
        ResponseEntity<Void> response = blogController.blogIdDelete(1L);
        assertNotNull(response);
    }

    @Test
    void blogIdGet() {
        when(blogService.blogIdGet(1L)).thenReturn(new BlogResponse());
        ResponseEntity<BlogResponse> response = blogController.blogIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void blogIdPut() {
        doNothing().when(blogService).blogIdPut(any(), any());
        ResponseEntity<Void> response = blogController.blogIdPut(1L, new BlogRequest());
        assertNotNull(response);
    }

    @Test
    void blogPost() {
        ImageUrlPost201Response serviceResponse = new ImageUrlPost201Response();
        serviceResponse.setId(1L);
        when(blogService.blogPost(any())).thenReturn(serviceResponse);
        ResponseEntity<ImageUrlPost201Response> response = blogController.blogPost(new BlogRequest());
        assertNotNull(response);
    }
}
