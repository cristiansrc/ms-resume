package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IImageUrlMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ImageUrlResponse imageUrlToImageUrlResponse(ImageUrlEntity imageUrlEntity);
}
