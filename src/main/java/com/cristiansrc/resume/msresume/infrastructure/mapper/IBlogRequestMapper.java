package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogRequest;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IBlogRequestMapper {

    @Mapping(target = "title", source = "title")
    @Mapping(target = "cleanUrlTitle", source = "cleanUrlTitle")
    @Mapping(target = "descriptionShort", source = "descriptionShort")
    @Mapping(target = "description", source = "description")
    void updateEntityFromBlogRequest(BlogRequest blogRequest, @MappingTarget BlogEntity blogEntity);
    BlogEntity toEntity(BlogRequest blogRequest);
}
