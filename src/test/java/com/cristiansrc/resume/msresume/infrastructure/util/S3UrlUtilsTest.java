package com.cristiansrc.resume.msresume.infrastructure.util;

import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3UrlUtilsTest {

    @Mock
    private IS3Service s3Service;

    @Test
    void setUrlFromEntity_success() {
        ImageUrlEntity entity = new ImageUrlEntity();
        entity.setNameFileAws("test-key");
        ImageUrlResponse response = new ImageUrlResponse();
        String expectedUrl = "http://example.com/test-key";

        when(s3Service.getAwsUrlFile("test-key")).thenReturn(expectedUrl);

        S3UrlUtils.setUrlFromEntity(s3Service, entity, response);

        assertEquals(expectedUrl, response.getUrl());
        verify(s3Service).getAwsUrlFile("test-key");
    }

    @Test
    void setUrlFromEntity_nullInputs() {
        S3UrlUtils.setUrlFromEntity(null, new ImageUrlEntity(), new ImageUrlResponse());
        S3UrlUtils.setUrlFromEntity(s3Service, null, new ImageUrlResponse());
        S3UrlUtils.setUrlFromEntity(s3Service, new ImageUrlEntity(), null);

        verifyNoInteractions(s3Service);
    }

    @Test
    void setUrlFromEntity_nullKey() {
        ImageUrlEntity entity = new ImageUrlEntity();
        entity.setNameFileAws(null);
        ImageUrlResponse response = new ImageUrlResponse();

        S3UrlUtils.setUrlFromEntity(s3Service, entity, response);

        assertNull(response.getUrl());
        verifyNoInteractions(s3Service);
    }

    @Test
    void setUrlFromEntity_s3Exception() {
        ImageUrlEntity entity = new ImageUrlEntity();
        entity.setNameFileAws("test-key");
        ImageUrlResponse response = new ImageUrlResponse();

        when(s3Service.getAwsUrlFile("test-key")).thenThrow(new RuntimeException("S3 Error"));

        S3UrlUtils.setUrlFromEntity(s3Service, entity, response);

        assertNull(response.getUrl());
        verify(s3Service).getAwsUrlFile("test-key");
    }

    @Test
    void setUrlsFromEntities_success() {
        ImageUrlEntity entity1 = new ImageUrlEntity();
        entity1.setNameFileAws("key1");
        ImageUrlEntity entity2 = new ImageUrlEntity();
        entity2.setNameFileAws("key2");
        List<ImageUrlEntity> entities = Arrays.asList(entity1, entity2);

        ImageUrlResponse response1 = new ImageUrlResponse();
        ImageUrlResponse response2 = new ImageUrlResponse();
        List<ImageUrlResponse> responses = Arrays.asList(response1, response2);

        when(s3Service.getAwsUrlFile("key1")).thenReturn("url1");
        when(s3Service.getAwsUrlFile("key2")).thenReturn("url2");

        S3UrlUtils.setUrlsFromEntities(s3Service, entities, responses);

        assertEquals("url1", response1.getUrl());
        assertEquals("url2", response2.getUrl());
    }

    @Test
    void setUrlsFromEntities_nullInputs() {
        S3UrlUtils.setUrlsFromEntities(null, Collections.emptyList(), Collections.emptyList());
        S3UrlUtils.setUrlsFromEntities(s3Service, null, Collections.emptyList());
        S3UrlUtils.setUrlsFromEntities(s3Service, Collections.emptyList(), null);

        verifyNoInteractions(s3Service);
    }

    @Test
    void setUrlsFromEntities_differentSizes() {
        ImageUrlEntity entity1 = new ImageUrlEntity();
        entity1.setNameFileAws("key1");
        List<ImageUrlEntity> entities = Collections.singletonList(entity1);

        ImageUrlResponse response1 = new ImageUrlResponse();
        ImageUrlResponse response2 = new ImageUrlResponse();
        List<ImageUrlResponse> responses = Arrays.asList(response1, response2);

        when(s3Service.getAwsUrlFile("key1")).thenReturn("url1");

        S3UrlUtils.setUrlsFromEntities(s3Service, entities, responses);

        assertEquals("url1", response1.getUrl());
        assertNull(response2.getUrl());
    }
}
