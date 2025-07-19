package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelResponse;

import java.util.List;

public interface ILabelService {
    List<LabelResponse> labelGet();
    void labelIdDelete(Long id);
    LabelResponse labelIdGet(Long id);
    ImageUrlPost201Response labelPost(LabelRequest labelRequest);
}
