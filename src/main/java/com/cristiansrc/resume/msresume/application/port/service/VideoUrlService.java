package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IVideoUrlService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBlogRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IVideoUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IVideoUrlMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.VideoUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoUrlService implements IVideoUrlService {

    private final IVideoUrlRepository videoUrlRepository;
    private final IVideoUrlMapper videoUrlMapper;
    private final MessageResolver messageResolver;
    private final IBlogRepository blogRepository;

    @Transactional(readOnly = true)
    @Override
    public List<VideoUrlResponse> videoUrlGet() {
        log.info("Fetching all video URLs");
        var videoUrlEntities = videoUrlRepository.findAllNotDeleted();
        var responseList = videoUrlMapper.videoUrlToVideoUrlResponseList(videoUrlEntities);
        log.info("Fetched {} video URLs", responseList.size());
        return responseList;
    }

    @Transactional
    @Override
    public void videoUrlIdDelete(Long id) {
        log.info("Deleting video with id: {}", id);
        var videoUrlEntity = getEntityById(id);
        
        if (blogRepository.existsByVideoUrlIdAndDeletedFalse(id)) {
            throw messageResolver.preconditionFailed("video.url.delete.precondition.failed", id);
        }

        videoUrlEntity.setDeleted(true);
        videoUrlRepository.save(videoUrlEntity);
        log.info("Deleted video url with id: {}", id);
    }

    @Transactional(readOnly = true)
    @Override
    public VideoUrlResponse videoUrlIdGet(Long id) {
        log.debug("Fetching video with id: {}", id);
        var videoUrlEntity = getEntityById(id);
        var response = videoUrlMapper.videoUrlToVideoUrlResponse(videoUrlEntity);
        log.debug("Fetched video url with id: {}", id);
        return response;
    }

    @Transactional
    @Override
    public ImageUrlPost201Response videoUrlPost(VideoUrlRequest videoUrlRequest) {
        log.info("Creating new image URL with name: {}", videoUrlRequest.getName());
        var videoUrlEntity = videoUrlMapper.toEntity(videoUrlRequest);
        videoUrlEntity = videoUrlRepository.save(videoUrlEntity);
        var response = new ImageUrlPost201Response();
        response.setId(videoUrlEntity.getId());
        log.info("Created new image URL with id: {}", videoUrlEntity.getId());
        return response;
    }

    private VideoUrlEntity getEntityById(Long id) {
        return videoUrlRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> messageResolver.notFound("video.url.not.found.byid", id));
    }
}
