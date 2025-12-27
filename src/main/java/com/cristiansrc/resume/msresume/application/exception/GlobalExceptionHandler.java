package com.cristiansrc.resume.msresume.application.exception;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import jakarta.servlet.http.HttpServletRequest;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ErrorResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ErrorResponseValidationErrorsInner;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.validation.BindException;
import org.springframework.dao.DataAccessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        logger.warn("Validation errors: {}", ex.getMessage());
        String msg = messageSource.getMessage("error.validation.failed", null, LocaleContextHolder.getLocale());
        ErrorResponse errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST, msg, request);
        populateValidationErrors(ex, errorResponse);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private void populateValidationErrors(MethodArgumentNotValidException ex, ErrorResponse errorResponse) {
        ex.getBindingResult().getFieldErrors().forEach(fe ->
            errorResponse.addValidationErrorsItem(new ErrorResponseValidationErrorsInner()
                    .field(fe.getField())
                    .message(fe.getDefaultMessage())));
    }

    @ExceptionHandler({NoHandlerFoundException.class, ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(Exception ex, HttpServletRequest request) {
        logger.warn("Resource not found: {}", ex.getMessage());
        String msg = messageSource.getMessage("error.resource.notfound", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.NOT_FOUND, msg, request);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsExceptions(InvalidCredentialsException ex, HttpServletRequest request) {
        logger.warn("Invalid credentials: {}", ex.getMessage());
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, HttpServletRequest request) {
        logger.error("Unhandled exception: ", ex);
        String msg = messageSource.getMessage("error.internal", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, msg, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        logger.warn("Type mismatch: {}", ex.getMessage());
        String msg = messageSource.getMessage(
                "error.type.mismatch", new Object[]{ex.getName()}, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(
            MissingServletRequestParameterException ex, HttpServletRequest request) {
        logger.warn("Missing parameter: {}", ex.getParameterName());
        String msg = messageSource.getMessage(
                "error.missing.param", new Object[]{ex.getParameterName()}, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        logger.warn("Malformed JSON request: {}", ex.getMessage());
        String msg = messageSource.getMessage(
                "error.message.not.readable", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        logger.warn("Method not allowed: {}", ex.getMethod());
        String msg = messageSource.getMessage(
                "error.method.not.allowed", new Object[]{ex.getMethod()}, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, msg, request);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        logger.warn("Unsupported media type: {}", ex.getContentType());
        String msg = messageSource.getMessage(
                "error.media.type.not.supported", new Object[]{ex.getContentType()}, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE, msg, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {
        logger.warn("Constraint violation: {}", ex.getMessage());
        String msg = messageSource.getMessage(
                "error.constraint.violation", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(
            BindException ex, HttpServletRequest request) {
        logger.warn("Bind error: {}", ex.getMessage());
        String msg = messageSource.getMessage(
                "error.bind", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException ex, HttpServletRequest request) {
        logger.warn("Access denied: {}", ex.getMessage());
        String msg = messageSource.getMessage(
                "error.access.denied", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.FORBIDDEN, msg, request);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(
            DataAccessException ex, HttpServletRequest request) {
        logger.error("Data access error: ", ex);
        String msg = messageSource.getMessage(
                "error.data.access", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, msg, request);
    }

    @ExceptionHandler(PreconditionFailedException.class)
    public ResponseEntity<ErrorResponse> handlePreconditionFailedException(
            PreconditionFailedException ex, HttpServletRequest request) {
        logger.error("Precondition failed: ", ex);
        String msg = messageSource.getMessage(
                "error.precondition.failed", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.PRECONDITION_FAILED, msg, request);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus status, String message, HttpServletRequest request) {
        return ResponseEntity.status(status).body(createErrorResponse(status, message, request));
    }

    private ErrorResponse createErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(OffsetDateTime.now());
        errorResponse.setStatus(status.value());
        errorResponse.setError(status.getReasonPhrase());
        errorResponse.setMessage(message);
        errorResponse.setPath(request.getRequestURI());
        return errorResponse;
    }
}
