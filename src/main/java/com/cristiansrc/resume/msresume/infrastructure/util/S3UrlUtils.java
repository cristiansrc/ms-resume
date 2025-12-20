package com.cristiansrc.resume.msresume.infrastructure.util;

import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;

import java.util.List;

public final class S3UrlUtils {

    private S3UrlUtils() {}

    public static void setUrlFromEntity(IS3Service s3Service, ImageUrlEntity entity, ImageUrlResponse response) {
        if (s3Service == null || entity == null || response == null) return;
        var key = entity.getNameFileAws();
        if (key != null) {
            try {
                var url = s3Service.getAwsUrlFile(key);
                response.setUrl(url);
            } catch (Exception ignored) {
                // Intencionalmente silencioso: no queremos romper el mapping por un fallo en S3
            }
        }
    }

    public static void setUrlsFromEntities(IS3Service s3Service, List<ImageUrlEntity> entities, List<ImageUrlResponse> responses) {
        if (entities == null || responses == null || s3Service == null) return;
        var size = Math.min(entities.size(), responses.size());
        for (int i = 0; i < size; i++) {
            setUrlFromEntity(s3Service, entities.get(i), responses.get(i));
        }
    }
}

