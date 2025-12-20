package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IImageUrlMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ImageUrlServiceTest {

    @Mock
    private IImageUrlRepository imageUrlRepository;

    @Mock
    private IImageUrlMapper imageUrlMapper;

    @Mock
    private MessageResolver messageResolver;

    @Mock
    private IS3Service s3Service;

    @InjectMocks
    private ImageUrlService imageUrlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void imageUrlGet() {
        ImageUrlEntity entity = new ImageUrlEntity();
        entity.setNameFileAws("test.txt");
        when(imageUrlRepository.findAllByDeletedFalse()).thenReturn(Collections.singletonList(entity));
        // Mockear primero S3
        when(s3Service.getAwsUrlFile(any())).thenReturn("http://test.com/test.txt");
        // El mapper ahora convierte listas y recibe IS3Service como contexto; devolverá response con URL ya seteada (simular afterMapping)
        ImageUrlResponse resp = new ImageUrlResponse();
        resp.setUrl("http://test.com/test.txt");
        when(imageUrlMapper.toImageUrlResponseList(any(), any())).thenReturn(Collections.singletonList(resp));

        List<ImageUrlResponse> response = imageUrlService.imageUrlGet();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("http://test.com/test.txt", response.get(0).getUrl());
    }

    @Test
    void imageUrlIdDelete() {
        ImageUrlEntity entity = new ImageUrlEntity();
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(imageUrlRepository.save(any())).thenReturn(entity);

        imageUrlService.imageUrlIdDelete(1L);
    }

    @Test
    void imageUrlIdGet() {
        ImageUrlEntity entity = new ImageUrlEntity();
        entity.setNameFileAws("test.txt");
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
        when(s3Service.getAwsUrlFile(any())).thenReturn("http://test.com/test.txt");
        ImageUrlResponse resp = new ImageUrlResponse();
        resp.setUrl("http://test.com/test.txt");
        when(imageUrlMapper.imageUrlToImageUrlResponse(any(), any())).thenReturn(resp);

        ImageUrlResponse response = imageUrlService.imageUrlIdGet(1L);

        assertNotNull(response);
        assertEquals("http://test.com/test.txt", response.getUrl());
    }

    @Test
    void imageUrlPost() {
        ImageUrlEntity entity = new ImageUrlEntity();
        ImageUrlRequest request = new ImageUrlRequest();
        request.setName("test");
        request.setFile("dGVzdA==");
        when(s3Service.uploadFile(any())).thenReturn("test.txt");
        when(imageUrlRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = imageUrlService.imageUrlPost(request);

        assertNotNull(response);
    }

    @Test
    void entityById_notFound() {
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("image.home.not.found"));

        assertThrows(RuntimeException.class, () -> imageUrlService.imageUrlIdGet(1L));
    }
}
