package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;

import java.util.List;

public interface ISkillSonService {
    List<SkillSonResponse> skillSonGet();
    void skillSonIdDelete(Long identifier);
    SkillSonResponse skillSonIdGet(Long identifier);
    void skillSonIdPut(Long identifier, SkillSonRequest skillSonRequest);
    ImageUrlPost201Response skillSonPost(SkillSonRequest skillSonRequest);
}
