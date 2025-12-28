package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;

import java.util.List;

public interface ISkillService {
    List<SkillResponse> skillGet();
    void skillIdDelete(Long identifier);
    SkillResponse skillIdGet(Long identifier);
    void skillIdPut(Long identifier, SkillRequest skillRequest);
    ImageUrlPost201Response skillPost(SkillRequest skillRequest);
}
