package com.cristiansrc.resume.msresume.application.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidCredentialsExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Test error message";
        InvalidCredentialsException exception = new InvalidCredentialsException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
