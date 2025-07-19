package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeResponse;

import java.util.List;

public interface ISkillTypeService {
    List<SkillTypeResponse> skillTypeGet();
    void skillTypeIdDelete(Long id);
    SkillTypeResponse skillTypeIdGet(Long id);
    void skillTypeIdPut(Long id, SkillTypeRequest skillTypeRequest);
    ImageUrlPost201Response skillTypePost(SkillTypeRequest skillTypeRequest);
}
