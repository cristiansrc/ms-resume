package com.cristiansrc.resume.msresume.application.exception;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ErrorResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ErrorResponseValidationErrorsInner;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            final MethodArgumentNotValidException exception,
            final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Validation errors: {}", exception.getMessage());
        }
        final String msg = messageSource.getMessage("error.validation.failed", null, LocaleContextHolder.getLocale());
        final ErrorResponse errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST, msg, request);
        populateValidationErrors(exception, errorResponse);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private void populateValidationErrors(final MethodArgumentNotValidException exception, final ErrorResponse errorResponse) {
        exception.getBindingResult().getFieldErrors().forEach(fe ->
                errorResponse.addValidationErrorsItem(new ErrorResponseValidationErrorsInner()
                        .field(fe.getField())
                        .message(fe.getDefaultMessage())));
    }

    @ExceptionHandler({NoHandlerFoundException.class, ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(final Exception exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Resource not found: {}", exception.getMessage());
        }
        final String msg = messageSource.getMessage("error.resource.notfound", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.NOT_FOUND, msg, request);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsExceptions(final InvalidCredentialsException exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Invalid credentials: {}", exception.getMessage());
        }
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, exception.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(final Exception exception, final HttpServletRequest request) {
        LOGGER.error("Unhandled exception: ", exception);
        final String msg = messageSource.getMessage("error.internal", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, msg, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            final MethodArgumentTypeMismatchException exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Type mismatch: {}", exception.getMessage());
        }
        final String msg = messageSource.getMessage(
                "error.type.mismatch", new Object[]{exception.getName()}, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(
            final MissingServletRequestParameterException exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Missing parameter: {}", exception.getParameterName());
        }
        final String msg = messageSource.getMessage(
                "error.missing.param", new Object[]{exception.getParameterName()}, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(
            final HttpMessageNotReadableException exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Malformed JSON request: {}", exception.getMessage());
        }
        final String msg = messageSource.getMessage(
                "error.message.not.readable", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            final HttpRequestMethodNotSupportedException exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Method not allowed: {}", exception.getMethod());
        }
        final String msg = messageSource.getMessage(
                "error.method.not.allowed", new Object[]{exception.getMethod()}, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, msg, request);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(
            final HttpMediaTypeNotSupportedException exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Unsupported media type: {}", exception.getContentType());
        }
        final String msg = messageSource.getMessage(
                "error.media.type.not.supported", new Object[]{exception.getContentType()}, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE, msg, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            final ConstraintViolationException exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Constraint violation: {}", exception.getMessage());
        }
        final String msg = messageSource.getMessage(
                "error.constraint.violation", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(
            final BindException exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Bind error: {}", exception.getMessage());
        }
        final String msg = messageSource.getMessage(
                "error.bind", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.BAD_REQUEST, msg, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            final AccessDeniedException exception, final HttpServletRequest request) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Access denied: {}", exception.getMessage());
        }
        final String msg = messageSource.getMessage(
                "error.access.denied", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.FORBIDDEN, msg, request);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(
            final DataAccessException exception, final HttpServletRequest request) {
        LOGGER.error("Data access error: ", exception);
        final String msg = messageSource.getMessage(
                "error.data.access", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, msg, request);
    }

    @ExceptionHandler(PreconditionFailedException.class)
    public ResponseEntity<ErrorResponse> handlePreconditionFailedException(
            final PreconditionFailedException exception, final HttpServletRequest request) {
        LOGGER.error("Precondition failed: ", exception);
        final String msg = messageSource.getMessage(
                "error.precondition.failed", null, LocaleContextHolder.getLocale());
        return buildResponseEntity(HttpStatus.PRECONDITION_FAILED, msg, request);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(final HttpStatus status, final String message, final HttpServletRequest request) {
        return ResponseEntity.status(status).body(createErrorResponse(status, message, request));
    }

    private ErrorResponse createErrorResponse(final HttpStatus status, final String message, final HttpServletRequest request) {
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(OffsetDateTime.now());
        errorResponse.setStatus(status.value());
        errorResponse.setError(status.getReasonPhrase());
        errorResponse.setMessage(message);
        errorResponse.setPath(request.getRequestURI());
        return errorResponse;
    }
}
