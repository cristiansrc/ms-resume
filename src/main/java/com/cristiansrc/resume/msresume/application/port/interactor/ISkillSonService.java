package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;

import java.util.List;

public interface ISkillSonService {
    List<SkillSonResponse> skillSonGet();
    void skillSonIdDelete(Long id);
    SkillSonResponse skillSonIdGet(Long id);
    void skillSonIdPut(Long id, SkillSonRequest skillSonRequest);
    ImageUrlPost201Response skillSonPost(SkillSonRequest skillSonRequest);
}
