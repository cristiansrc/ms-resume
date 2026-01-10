package com.cristiansrc.resume.msresume.application.exception;

public class RenderCvServiceException extends RuntimeException {
    public RenderCvServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    public RenderCvServiceException(String message) {
        super(message);
    }
}
