package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IAltchaService;
import com.cristiansrc.resume.msresume.application.port.output.client.ITelegramClient;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ContactRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

class ContactServiceTest {

    @Mock
    private ITelegramClient telegramClient;

    @Mock
    private IAltchaService altchaService;

    @InjectMocks
    private ContactService contactService;

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
    void sendContactMessage_success() {
        // Arrange
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setName("John Doe");
        contactRequest.setEmail("john.doe@example.com");
        contactRequest.setMessage("Hello");
        contactRequest.setAltcha("valid-altcha");

        // Act
        contactService.sendContactMessage(contactRequest);

        // Assert
        verify(altchaService).validateChallenge("valid-altcha");
        verify(telegramClient).sendMessage(anyString());
    }
}
