package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IAltchaService;
import com.cristiansrc.resume.msresume.application.port.interactor.IContactService;
import com.cristiansrc.resume.msresume.application.port.output.client.ITelegramClient;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ContactRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactService implements IContactService {

    private final ITelegramClient telegramClient;
    private final IAltchaService altchaService;

    @Override
    public void sendContactMessage(ContactRequest contactRequest) {
        log.info("Sending contact message from: {}", contactRequest.getEmail());
        
        altchaService.validateChallenge(contactRequest.getAltcha());

        String message = String.format(
                "New Contact Message:%n%nName: %s%nEmail: %s%nMessage: %s",
                contactRequest.getName(),
                contactRequest.getEmail(),
                contactRequest.getMessage()
        );
        telegramClient.sendMessage(message);
    }
}
