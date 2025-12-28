package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;

public interface IBasicDataService {
    BasicDataResponse basicDataIdGet(Long identifier);
    void basicDataIdPut(Long identifier, BasicDataRequest basicDataRequest);
}