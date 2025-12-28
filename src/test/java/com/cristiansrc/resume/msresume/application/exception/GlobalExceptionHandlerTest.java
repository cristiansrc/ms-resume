package com.cristiansrc.resume.msresume.application.exception;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

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
    void handleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        when(messageSource.getMessage(eq("error.validation.failed"), isNull(), any(Locale.class)))
                .thenReturn("Validation failed");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationExceptions(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed", response.getBody().getMessage());
        assertEquals(1, response.getBody().getValidationErrors().size());
    }

    @Test
    void handleNotFoundExceptions() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        when(messageSource.getMessage(eq("error.resource.notfound"), isNull(), any(Locale.class)))
                .thenReturn("Resource not found");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNotFoundExceptions(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    @Test
    void handleInvalidCredentialsExceptions() {
        String errorMessage = "Invalid credentials";
        InvalidCredentialsException ex = new InvalidCredentialsException(errorMessage);
        MockHttpServletRequest request = new MockHttpServletRequest();

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleInvalidCredentialsExceptions(ex, request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody().getMessage());
    }

    @Test
    void handleAllExceptions() {
        Exception ex = new Exception("Internal error");
        when(messageSource.getMessage(eq("error.internal"), isNull(), any(Locale.class)))
                .thenReturn("Internal Server Error");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleAllExceptions(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody().getMessage());
    }

    @Test
    void handleTypeMismatch() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("paramName");
        when(messageSource.getMessage(eq("error.type.mismatch"), any(), any(Locale.class)))
                .thenReturn("Type mismatch");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleTypeMismatch(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Type mismatch", response.getBody().getMessage());
    }

    @Test
    void handleMissingParam() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("param", "String");
        when(messageSource.getMessage(eq("error.missing.param"), any(), any(Locale.class)))
                .thenReturn("Missing parameter");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMissingParam(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Missing parameter", response.getBody().getMessage());
    }

    @Test
    void handleMessageNotReadable() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Not readable");
        when(messageSource.getMessage(eq("error.message.not.readable"), isNull(), any(Locale.class)))
                .thenReturn("Message not readable");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Message not readable", response.getBody().getMessage());
    }

    @Test
    void handleMethodNotSupported() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST");
        when(messageSource.getMessage(eq("error.method.not.allowed"), any(), any(Locale.class)))
                .thenReturn("Method not allowed");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMethodNotSupported(ex, request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals("Method not allowed", response.getBody().getMessage());
    }

    @Test
    void handleMediaTypeNotSupported() {
        HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException(MediaType.APPLICATION_JSON.getType());
        when(messageSource.getMessage(eq("error.media.type.not.supported"), any(), any(Locale.class)))
                .thenReturn("Media type not supported");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMediaTypeNotSupported(ex, request);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        assertEquals("Media type not supported", response.getBody().getMessage());
    }

    @Test
    void handleConstraintViolation() {
        ConstraintViolationException ex = new ConstraintViolationException("Violation", Collections.emptySet());
        when(messageSource.getMessage(eq("error.constraint.violation"), isNull(), any(Locale.class)))
                .thenReturn("Constraint violation");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleConstraintViolation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Constraint violation", response.getBody().getMessage());
    }

    @Test
    void handleBindException() {
        BindException ex = new BindException(new Object(), "objectName");
        when(messageSource.getMessage(eq("error.bind"), isNull(), any(Locale.class)))
                .thenReturn("Bind error");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleBindException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bind error", response.getBody().getMessage());
    }

    @Test
    void handleAccessDenied() {
        AccessDeniedException ex = new AccessDeniedException("Access denied");
        when(messageSource.getMessage(eq("error.access.denied"), isNull(), any(Locale.class)))
                .thenReturn("Access denied");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleAccessDenied(ex, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access denied", response.getBody().getMessage());
    }

    @Test
    void handleDataAccessException() {
        DataAccessException ex = new DataAccessException("Data access error") {};
        when(messageSource.getMessage(eq("error.data.access"), isNull(), any(Locale.class)))
                .thenReturn("Data access error");

        MockHttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDataAccessException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Data access error", response.getBody().getMessage());
    }

    @Test
    void handlePreconditionFailedException() {
        String errorMessage = "Precondition failed";
        when(messageSource.getMessage(eq("error.precondition.failed"), isNull(), any(Locale.class))).thenReturn(errorMessage);
        PreconditionFailedException ex = new PreconditionFailedException("Some detail");
        MockHttpServletRequest request = new MockHttpServletRequest();

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handlePreconditionFailedException(ex, request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.PRECONDITION_FAILED.value(), errorResponse.getStatus());
        assertEquals(errorMessage, errorResponse.getMessage());
    }
}
