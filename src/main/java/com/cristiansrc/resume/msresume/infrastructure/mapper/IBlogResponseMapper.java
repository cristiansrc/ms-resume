package com.cristiansrc.resume.msresume.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogEntity;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;

@Mapper(componentModel = "spring")
public interface IBlogResponseMapper {

    @Mapping(target = "content", source = "content")
    @Mapping(target = "pageable", source = "pageable")
    @Mapping(target = "last", source = "last")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "sort", source = "sort")
    @Mapping(target = "first", source = "first")
    @Mapping(target = "numberOfElements", source = "numberOfElements")
    BlogPageResponse toPageResponse(Page<BlogEntity> page);
    BlogResponse toResponse(BlogEntity entity);
}