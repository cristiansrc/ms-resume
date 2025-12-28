package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;

public interface IHomeService {
    HomeResponse homeIdGet(Long identifier);
    void homeIdPut(Long identifier, HomeRequest homeRequest);
}
