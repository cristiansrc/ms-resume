package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;

import java.util.List;

public interface IFuturedProjectService {
    List<FuturedProjectResponse> futuredProjectGet();
    void futuredProjectIdDelete(Long id);
    FuturedProjectResponse futuredProjectIdGet(Long id);
    void futuredProjectIdPut(Long id, FuturedProjectRequest futuredProjectRequest);
    ImageUrlPost201Response futuredProjectPost(FuturedProjectRequest futuredProjectRequest);
}
