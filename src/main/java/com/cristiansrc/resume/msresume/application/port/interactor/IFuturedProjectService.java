package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;

import java.util.List;

public interface IFuturedProjectService {
    List<FuturedProjectResponse> futuredProjectGet();
    void futuredProjectIdDelete(Long identifier);
    FuturedProjectResponse futuredProjectIdGet(Long identifier);
    void futuredProjectIdPut(Long identifier, FuturedProjectRequest request);
    ImageUrlPost201Response futuredProjectPost(FuturedProjectRequest request);
}
