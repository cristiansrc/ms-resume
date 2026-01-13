package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ContactRequest;

public interface IContactService {
    void sendContactMessage(ContactRequest contactRequest);
}
