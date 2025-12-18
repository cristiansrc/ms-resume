package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.VideoUrlEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVideoUrlMapper {
    VideoUrlEntity toEntity(VideoUrlRequest videoUrlRequest);
    VideoUrlResponse videoUrlToVideoUrlResponse(VideoUrlEntity videoUrlEntity);
    List<VideoUrlResponse> videoUrlToVideoUrlResponseList(List<VideoUrlEntity> videoUrlEntities);
}
