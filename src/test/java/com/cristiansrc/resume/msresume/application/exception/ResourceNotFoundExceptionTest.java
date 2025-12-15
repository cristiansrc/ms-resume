package com.cristiansrc.resume.msresume.application.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceNotFoundExceptionTest {

    @Test
    void testException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Test message");
        assertEquals("Test message", exception.getMessage());
    }
}
