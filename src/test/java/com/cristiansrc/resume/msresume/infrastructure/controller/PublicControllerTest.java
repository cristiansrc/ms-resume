package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IBlogService;
import com.cristiansrc.resume.msresume.application.port.interactor.IBlogTypeService;
import com.cristiansrc.resume.msresume.application.port.interactor.IPublicService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ContactRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.InfoPageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
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
    void publicBlogGet() {
        BlogPageResponse mockResponse = new BlogPageResponse();
        when(blogService.blogGet(0, 10, "id,desc")).thenReturn(mockResponse);

        ResponseEntity<BlogPageResponse> response = publicController.publicBlogGet(0, 10, "id,desc");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(blogService).blogGet(0, 10, "id,desc");
    }

    @Test
    void publicBlogIdGet() {
        BlogResponse mockResponse = new BlogResponse();
        when(blogService.blogIdGet(1L)).thenReturn(mockResponse);

        ResponseEntity<BlogResponse> response = publicController.publicBlogIdGet(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(blogService).blogIdGet(1L);
    }

    @Test
    void publicBlogTypeGet() {
        List<BlogTypeResponse> mockResponse = Collections.singletonList(new BlogTypeResponse());
        when(blogTypeService.blogTypeGet()).thenReturn(mockResponse);

        ResponseEntity<List<BlogTypeResponse>> response = publicController.publicBlogTypeGet();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(blogTypeService).blogTypeGet();
    }

    @Test
    void publicBlogTypeIdGet() {
        BlogTypeResponse mockResponse = new BlogTypeResponse();
        when(blogTypeService.blogTypeIdGet(1L)).thenReturn(mockResponse);

        ResponseEntity<BlogTypeResponse> response = publicController.publicBlogTypeIdGet(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(blogTypeService).blogTypeIdGet(1L);
    }

    @Test
    void publicInfoPageGet() {
        InfoPageResponse mockResponse = new InfoPageResponse();
        when(publicService.getInfoPage()).thenReturn(mockResponse);

        ResponseEntity<InfoPageResponse> response = publicController.publicInfoPageGet();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(publicService).getInfoPage();
    }

    @Test
    void publicContactPost() {
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setName("Test");
        contactRequest.setEmail("test@test.com");
        contactRequest.setMessage("Message");

        ResponseEntity<Void> response = publicController.publicContactPost(contactRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(publicService).sendContactMessage(contactRequest);
    }
}
