package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IImageUrlService;
import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IImageUrlMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import com.cristiansrc.resume.msresume.infrastructure.util.MultipartUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageUrlService implements IImageUrlService {

    private final IImageUrlRepository imageUrlRepository;
    private final IImageUrlMapper imageUrlMapper;
    private final MessageResolver messageResolver;
    private final IS3Service s3Service;

    @Transactional(readOnly = true)
    @Override
    public List<ImageUrlResponse> imageUrlGet() {
        log.info("Fetching all image URLs");
        var listEntities = imageUrlRepository.findAllByDeletedFalse();
        var listResponse = imageUrlMapper.toImageUrlResponseList(listEntities, s3Service);
        log.info("Fetched {} image URLs", listResponse.size());
        return listResponse;
    }

    @Transactional
    @Override
    public void imageUrlIdDelete(Long id) {
        log.info("Deleting image URL with id: {}", id);
        var entity = entityById(id);
        entity.setDeleted(true);
        imageUrlRepository.save(entity);
        log.info("Image URL with id: {} deleted successfully", id);
    }

    @Transactional(readOnly = true)
    @Override
    public ImageUrlResponse imageUrlIdGet(Long id) {
        log.info("Fetching image URL with id: {}", id);
        var entity = entityById(id);
        var response = imageUrlMapper.imageUrlToImageUrlResponse(entity, s3Service);
        log.info("Fetched image URL with id: {}", id);
        return response;
    }

    @Transactional
    @Override
    public ImageUrlPost201Response imageUrlPost(ImageUrlRequest imageUrlRequest) {
        log.info("Creating new image URL with name: {}", imageUrlRequest.getName());
        var file = MultipartUtils.base64ToMultipart(imageUrlRequest.getFile(), imageUrlRequest.getName());
        var nameFileAws = s3Service.uploadFile(file);
        var entity = new ImageUrlEntity();
        entity.setName(imageUrlRequest.getName());
        entity.setNameEng(imageUrlRequest.getNameEng());
        entity.setNameFileAws(nameFileAws);
        var savedEntity = imageUrlRepository.save(entity);
        log.info("Created new image URL with id: {}", savedEntity.getId());
        var responde = new ImageUrlPost201Response();
        responde.setId(savedEntity.getId());
        return responde;
    }


    private ImageUrlEntity entityById(Long id) {
        return imageUrlRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> messageResolver.notFound("image.home.not.found", id));
    }

    private ImageUrlResponse imageUrlToImageUrlResponse(ImageUrlEntity entity) {
        // Mantengo este helper en caso de uso interno; delega al mapper con contexto
        return imageUrlMapper.imageUrlToImageUrlResponse(entity, s3Service);
    }
}
