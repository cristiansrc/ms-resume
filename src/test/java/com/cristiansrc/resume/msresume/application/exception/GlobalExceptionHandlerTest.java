package com.cristiansrc.resume.msresume.application.exception;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("error.validation.failed", Locale.getDefault(), "validation failed");
        messageSource.addMessage("error.resource.notfound", Locale.getDefault(), "resource not found");
        messageSource.addMessage("error.internal", Locale.getDefault(), "internal server error");
        messageSource.addMessage("error.type.mismatch", Locale.getDefault(), "type mismatch for {0}");
        messageSource.addMessage("error.missing.param", Locale.getDefault(), "missing parameter: {0}");
        messageSource.addMessage("error.message.not.readable", Locale.getDefault(), "malformed JSON request");
        messageSource.addMessage("error.method.not.allowed", Locale.getDefault(), "method not allowed: {0}");
        messageSource.addMessage("error.media.type.not.supported", Locale.getDefault(), "media type not supported: {0}");
        messageSource.addMessage("error.constraint.violation", Locale.getDefault(), "constraint violation");
        messageSource.addMessage("error.bind", Locale.getDefault(), "bind error");
        messageSource.addMessage("error.access.denied", Locale.getDefault(), "access denied");
        messageSource.addMessage("error.data.access", Locale.getDefault(), "data access error");

        handler = new GlobalExceptionHandler(messageSource);
        request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
    }

    @Test
    void handleValidationExceptions_populatesErrors() throws NoSuchMethodException {
        SomeBean bean = new SomeBean();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(bean, "someBean");
        bindingResult.addError(new FieldError("someBean", "field1", "must not be null"));
        MethodParameter methodParameter = new MethodParameter(this.getClass().getDeclaredMethod("testMethod", SomeBean.class), 0);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<ErrorResponse> responseEntity = handler.handleValidationExceptions(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertEquals("validation failed", resp.getMessage());
        assertNotNull(resp.getValidationErrors());
        assertTrue(resp.getValidationErrors().stream().anyMatch(v -> "field1".equals(v.getField())));
    }

    @Test
    void handleNotFoundExceptions() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleNotFoundExceptions(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCode().value());
        assertEquals("resource not found", resp.getMessage());
    }

    @Test
    void handleAllExceptions() {
        Exception ex = new Exception("Some generic error");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleAllExceptions(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCode().value());
        assertEquals("internal server error", resp.getMessage());
    }

    @Test
    void handleTypeMismatch() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException("value", String.class, "param", null, null);
        ResponseEntity<ErrorResponse> responseEntity = handler.handleTypeMismatch(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertTrue(resp.getMessage().contains("type mismatch for param"));
    }

    @Test
    void handleMissingParam() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("paramName", "String");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleMissingParam(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertTrue(resp.getMessage().contains("missing parameter: paramName"));
    }

    @Test
    void handleMessageNotReadable() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("msg");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleMessageNotReadable(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertEquals("malformed JSON request", resp.getMessage());
    }

    @Test
    void handleMethodNotSupported() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleMethodNotSupported(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), responseEntity.getStatusCode().value());
        assertTrue(resp.getMessage().contains("method not allowed: POST"));
    }

    @Test
    void handleMediaTypeNotSupported() {
        HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException(MediaType.APPLICATION_JSON, Collections.singletonList(MediaType.APPLICATION_XML));
        ResponseEntity<ErrorResponse> responseEntity = handler.handleMediaTypeNotSupported(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), responseEntity.getStatusCode().value());
        assertTrue(resp.getMessage().contains("media type not supported: application/json"));
    }

    @Test
    void handleConstraintViolation() {
        ConstraintViolationException ex = new ConstraintViolationException("violation", Collections.emptySet());
        ResponseEntity<ErrorResponse> responseEntity = handler.handleConstraintViolation(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertEquals("constraint violation", resp.getMessage());
    }

    @Test
    void handleBindException() {
        BindException ex = new BindException(new Object(), "objectName");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleBindException(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        assertEquals("bind error", resp.getMessage());
    }

    @Test
    void handleAccessDenied() {
        AccessDeniedException ex = new AccessDeniedException("access denied");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleAccessDenied(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN.value(), responseEntity.getStatusCode().value());
        assertEquals("access denied", resp.getMessage());
    }

    @Test
    void handleDataAccessException() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("data access error");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleDataAccessException(ex, request);
        ErrorResponse resp = responseEntity.getBody();

        assertNotNull(resp);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCode().value());
        assertEquals("data access error", resp.getMessage());
    }

    public void testMethod(SomeBean someBean) {
    }

    static class SomeBean {}
}
