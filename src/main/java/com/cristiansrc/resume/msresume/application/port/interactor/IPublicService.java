package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ContactRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.InfoPageResponse;
import org.springframework.core.io.Resource;

public interface IPublicService {
    InfoPageResponse getInfoPage();
    Resource publicCurriculumGet(String language);
    void sendContactMessage(ContactRequest contactRequest);
}
