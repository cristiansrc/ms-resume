package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBlogTypeMapper {

    @Mapping(target = "name", source = "name")
    BlogTypeEntity toEntity(BlogTypeRequest blogTypeRequest);
    void updateEntity(BlogTypeRequest blogTypeRequest, @MappingTarget BlogTypeEntity blogTypeEntity);

    @Mapping(target = "id", source = "id")
    BlogTypeResponse toResponse(BlogTypeEntity blogTypeEntity);
    List<BlogTypeResponse> toResponseList(List<BlogTypeEntity> blogTypeEntities);

}
