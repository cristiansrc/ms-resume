package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IImageUrlService;
import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IFuturedProjectRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IImageUrlMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import com.cristiansrc.resume.msresume.infrastructure.util.MultipartUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageUrlService implements IImageUrlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUrlService.class);
    private final IImageUrlRepository repository;
    private final IImageUrlMapper mapper;
    private final MessageResolver messageResolver;
    private final IS3Service s3Service;
    private final IFuturedProjectRepository futuredProjectRepo;
    private final IHomeRepository homeRepo;

    @Transactional(readOnly = true)
    @Override
    public List<ImageUrlResponse> imageUrlGet() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching all image URLs");
        }
        final List<ImageUrlEntity> listEntities = repository.findAllByDeletedFalse();
        final List<ImageUrlResponse> listResponse = mapper.toImageUrlResponseList(listEntities, s3Service);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetched {} image URLs", listResponse.size());
        }
        return listResponse;
    }

    @Transactional
    @Override
    public void imageUrlIdDelete(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleting image URL with id: {}", identifier);
        }
        final ImageUrlEntity entity = entityById(identifier);

        if (futuredProjectRepo.existsByImageUrlIdAndDeletedFalse(identifier) ||
                futuredProjectRepo.existsByImageListUrlIdAndDeletedFalse(identifier) ||
                homeRepo.existsByImageUrlIdAndDeletedFalse(identifier)) {
            throw messageResolver.preconditionFailed("image.url.delete.precondition.failed", identifier);
        }

        entity.setDeleted(true);
        repository.save(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Image URL with id: {} deleted successfully", identifier);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ImageUrlResponse imageUrlIdGet(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetching image URL with id: {}", identifier);
        }
        final ImageUrlEntity entity = entityById(identifier);
        final ImageUrlResponse response = mapper.imageUrlToImageUrlResponse(entity, s3Service);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetched image URL with id: {}", identifier);
        }
        return response;
    }

    @Transactional
    @Override
    public ImageUrlPost201Response imageUrlPost(final ImageUrlRequest imageUrlRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new image URL with name: {}", imageUrlRequest.getName());
        }
        final MultipartFile file = MultipartUtils.base64ToMultipart(imageUrlRequest.getFile(), imageUrlRequest.getName());
        final String nameFileAws = s3Service.uploadFile(file);
        final ImageUrlEntity entity = new ImageUrlEntity();
        entity.setName(imageUrlRequest.getName());
        entity.setNameEng(imageUrlRequest.getNameEng());
        entity.setNameFileAws(nameFileAws);
        final ImageUrlEntity savedEntity = repository.save(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Created new image URL with id: {}", savedEntity.getId());
        }
        final ImageUrlPost201Response response = new ImageUrlPost201Response();
        response.setId(savedEntity.getId());
        return response;
    }


    private ImageUrlEntity entityById(final Long identifier) {
        return repository.findByIdAndDeletedFalse(identifier)
                .orElseThrow(() -> messageResolver.notFound("image.home.not.found", identifier));
    }
}
