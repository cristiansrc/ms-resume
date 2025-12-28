package com.cristiansrc.resume.msresume.application.exception;

public class PreconditionFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PreconditionFailedException(final String message) {
        super(message);
    }
}
