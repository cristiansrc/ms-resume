package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;

public interface IHomeService {
    HomeResponse homeIdGet(Long id);
    void homeIdPut(Long id, HomeRequest homeRequest);
}
