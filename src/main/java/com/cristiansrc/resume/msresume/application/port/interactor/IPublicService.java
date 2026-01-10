package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.InfoPageResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface IPublicService {
    InfoPageResponse getInfoPage();
    Resource publicCurriculumGet(String language);
}
