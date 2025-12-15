package com.cristiansrc.resume.msresume.infrastructure.util;

import com.cristiansrc.resume.msresume.application.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void getMessage() {
        when(messageSource.getMessage(eq("test.code"), any(), any(Locale.class))).thenReturn("test message");
        String message = messageResolver.getMessage("test.code");
        assertNotNull(message);
    }

    @Test
    void notFound() {
        when(messageSource.getMessage(any(), any(), any())).thenReturn("test message");
        ResourceNotFoundException exception = messageResolver.notFound("test.code");
        assertNotNull(exception);
    }
}
