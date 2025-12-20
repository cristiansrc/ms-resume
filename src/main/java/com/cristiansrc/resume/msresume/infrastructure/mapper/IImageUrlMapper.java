package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.S3UrlUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IImageUrlMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ImageUrlResponse imageUrlToImageUrlResponse(ImageUrlEntity imageUrlEntity, @Context IS3Service s3Service);

    List<ImageUrlResponse> toImageUrlResponseList(List<ImageUrlEntity> imageUrlEntities, @Context IS3Service s3Service);

    @AfterMapping
    default void afterImageUrlToImageUrlResponse(ImageUrlEntity entity, @MappingTarget ImageUrlResponse response, @Context IS3Service s3Service) {
        S3UrlUtils.setUrlFromEntity(s3Service, entity, response);
    }

}
