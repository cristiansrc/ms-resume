package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IBlogService;
import com.cristiansrc.resume.msresume.application.port.interactor.IBlogTypeService;
import com.cristiansrc.resume.msresume.application.port.interactor.IPublicService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.InfoPageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PublicControllerTest {

    @Mock
    private IBlogService blogService;
    @Mock
    private IBlogTypeService blogTypeService;
    @Mock
    private IPublicService publicService;

    @InjectMocks
    private PublicController publicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void publicBlogGet() {
        when(blogService.blogGet(any(), any(), any())).thenReturn(new BlogPageResponse());
        ResponseEntity<BlogPageResponse> response = publicController.publicBlogGet(0, 10, "DESC");
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void publicBlogIdGet() {
        when(blogService.blogIdGet(1L)).thenReturn(new BlogResponse());
        ResponseEntity<BlogResponse> response = publicController.publicBlogIdGet(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void publicBlogTypeGet() {
        when(blogTypeService.blogTypeGet()).thenReturn(Collections.singletonList(new BlogTypeResponse()));
        ResponseEntity<List<BlogTypeResponse>> response = publicController.publicBlogTypeGet();
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void publicBlogTypeIdGet() {
        when(blogTypeService.blogTypeIdGet(1L)).thenReturn(new BlogTypeResponse());
        ResponseEntity<BlogTypeResponse> response = publicController.publicBlogTypeIdGet(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void publicInfoPageGet() {
        when(publicService.getInfoPage()).thenReturn(new InfoPageResponse());
        ResponseEntity<InfoPageResponse> response = publicController.publicInfoPageGet();
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }
}
