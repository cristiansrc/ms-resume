package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.FuturedProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IFuturedProject {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "descriptionShort", source = "descriptionShort")
    @Mapping(target = "description", source = "description")
    FuturedProjectEntity toFuturedProjectEntity(FuturedProjectRequest futuredProjectRequest);
    void updateFuturedProjectEntityFromRequest(
            FuturedProjectRequest futuredProjectRequest, @MappingTarget FuturedProjectEntity futuredProjectEntity);

    @Mapping(target = "id", source = "id")
    FuturedProjectResponse toFuturedProjectResponse(FuturedProjectEntity futuredProjectEntity);
}
