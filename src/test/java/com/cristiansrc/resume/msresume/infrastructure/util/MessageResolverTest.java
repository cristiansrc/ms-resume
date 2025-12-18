package com.cristiansrc.resume.msresume.infrastructure.util;

import com.cristiansrc.resume.msresume.application.exception.InvalidCredentialsException;
import com.cristiansrc.resume.msresume.application.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class MessageResolverTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MessageResolver messageResolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void notFound() {
        String errorMessage = "Resource not found";
        when(messageSource.getMessage(eq("key"), any(), any(Locale.class))).thenReturn(errorMessage);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            throw messageResolver.notFound("key");
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void invalidCredentials() {
        String errorMessage = "Invalid credentials";
        when(messageSource.getMessage(eq("key"), any(), any(Locale.class))).thenReturn(errorMessage);

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            throw messageResolver.invalidCredentials("key");
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}
