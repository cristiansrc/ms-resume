package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IHomeMapper {

    @Mapping(target = "greeting", source = "greeting")
    @Mapping(target = "buttonWorkLabel", source = "buttonWorkLabel")
    @Mapping(target = "buttonContactLabel", source = "buttonContactLabel")
    void updateEntityFromRequest(
            HomeRequest homeRequest, @MappingTarget HomeEntity homeEntity);

    @Mapping(target = "id", source = "id")
    HomeResponse toResponse(HomeEntity homeEntity);
}
