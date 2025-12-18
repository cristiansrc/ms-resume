package com.cristiansrc.resume.msresume.application.exception;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleInvalidCredentialsExceptions() {
        String errorMessage = "Invalid credentials";
        when(messageSource.getMessage(eq("error.invalid.credentials"), any(), any(Locale.class))).thenReturn(errorMessage);
        InvalidCredentialsException ex = new InvalidCredentialsException(errorMessage);
        MockHttpServletRequest request = new MockHttpServletRequest();

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleInvalidCredentialsExceptions(ex, request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), errorResponse.getStatus());
        assertEquals(HttpStatus.UNAUTHORIZED.getReasonPhrase(), errorResponse.getError());
        assertEquals(errorMessage, errorResponse.getMessage());
    }
}
