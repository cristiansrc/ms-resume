package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.S3UrlUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IHomeMapper {

    @Mapping(target = "greeting", source = "greeting")
    @Mapping(target = "buttonWorkLabel", source = "buttonWorkLabel")
    @Mapping(target = "buttonContactLabel", source = "buttonContactLabel")
    void updateEntityFromRequest(
            HomeRequest homeRequest, @MappingTarget HomeEntity homeEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "labels", ignore = true)
    HomeResponse toResponse(HomeEntity homeEntity, @Context IS3Service s3Service);

    @AfterMapping
    default void afterToResponse(HomeEntity homeEntity, @MappingTarget HomeResponse homeResponse, @Context IS3Service s3Service) {
        if (homeEntity.getImageUrl() != null && homeResponse.getImageUrl() != null) {
            S3UrlUtils.setUrlFromEntity(s3Service, homeEntity.getImageUrl(), homeResponse.getImageUrl());
        }
        
        if (homeEntity.getHomeLabelRelations() != null) {
            List<LabelResponse> labelResponses = homeEntity.getHomeLabelRelations().stream()
                .map(rel -> {
                    LabelResponse lr = new LabelResponse();
                    lr.setId(rel.getLabel().getId());
                    lr.setName(rel.getLabel().getName());
                    lr.setNameEng(rel.getLabel().getNameEng());
                    return lr;
                })
                .toList();
            homeResponse.setLabels(labelResponses);
        }
    }
}
