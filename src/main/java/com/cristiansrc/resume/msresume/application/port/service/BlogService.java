package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IBlogService;
import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBlogRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IImageUrlRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IVideoUrlRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBlogRequestMapper;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBlogResponseMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogService implements IBlogService {

    private final IBlogRepository blogRepository;
    private final IBlogResponseMapper blogResponseMapper;
    private final MessageResolver messageResolver;
    private final IBlogRequestMapper blogRequestMapper;
    private final IImageUrlRepository imageUrlRepository;
    private final IVideoUrlRepository videoUrlRepository;
    private final IS3Service s3Service;

    @Transactional(readOnly = true)
    @Override
    public BlogPageResponse blogGet(Integer page, Integer size, String sortParam) {
        log.debug("Fetching blog posts with page: {}, size: {}, sort: {}", page, size, sortParam);

        page = (page == null || page < 0) ? 0 : page;
        size = (size == null || size < 1) ? 10 : size;
        sortParam = (sortParam == null || sortParam.isBlank()) ? "id,desc" : sortParam;

        String[] sortParts = sortParam.split(",");
        String property = sortParts[0];
        Sort.Direction direction = (sortParts.length > 1) ? Sort.Direction.fromString(sortParts[1]) : Sort.Direction.DESC;

        var sort = Sort.by(direction, property);
        var pageable = PageRequest.of(page, size, sort);

        var entityPage = blogRepository.findByTitleContainingIgnoreCaseAndSort(null, pageable);
        var models = blogResponseMapper.toPageResponse(entityPage, s3Service);
        log.debug("Fetched {} blog posts", models.getContent().size());
        return models;
    }

    @Transactional
    @Override
    public void blogIdDelete(Long id) {
        log.info("Deleting blog post with id: {}", id);
        var entity = getEntityById(id);
        entity.setDeleted(true);
        blogRepository.save(entity);
        log.info("Blog post with id: {} deleted successfully", id);
    }

    @Transactional(readOnly = true)
    @Override
    public BlogResponse blogIdGet(Long id) {
        log.debug("Fetching blog post with id: {}", id);
        var entity = getEntityById(id);
        var response = blogResponseMapper.toResponse(entity, s3Service);
        log.debug("Fetched blog post with id: {}", id);
        return response;
    }

    @Transactional
    @Override
    public void blogIdPut(Long id, BlogRequest blogRequest) {
        log.info("Updating blog post with id: {}", id);
        var entity = getEntityById(id);

        String cleanUrlTitle = processCleanUrlTitle(blogRequest);
        
        if (!entity.getCleanUrlTitle().equals(cleanUrlTitle) && blogRepository.existsByCleanUrlTitleAndDeletedFalse(cleanUrlTitle)) {
            throw messageResolver.preconditionFailed("blog.clean.url.title.exists", cleanUrlTitle);
        }
        
        blogRequest.setCleanUrlTitle(cleanUrlTitle);
        blogRequestMapper.updateEntityFromBlogRequest(blogRequest, entity);
        
        if (blogRequest.getImageUrlId() != null) {
            var image = imageUrlRepository.findByIdAndDeletedFalse(blogRequest.getImageUrlId())
                    .orElseThrow(() -> messageResolver.notFound("image.url.not.found", blogRequest.getImageUrlId()));
            entity.setImageUrl(image);
        } else {
            entity.setImageUrl(null);
        }

        if (blogRequest.getVideoUrlId() != null) {
            var video = videoUrlRepository.findByIdAndNotDeleted(blogRequest.getVideoUrlId())
                    .orElseThrow(() -> messageResolver.notFound("video.url.not.found", blogRequest.getVideoUrlId()));
            entity.setVideoUrl(video);
        } else {
            entity.setVideoUrl(null);
        }

        blogRepository.save(entity);
        log.info("Blog post with id: {} updated successfully", id);
    }

    @Transactional
    @Override
    public ImageUrlPost201Response blogPost(BlogRequest blogRequest) {
        log.info("Creating new blog post");

        String cleanUrlTitle = processCleanUrlTitle(blogRequest);
        blogRequest.setCleanUrlTitle(cleanUrlTitle);

        if (blogRepository.existsByCleanUrlTitleAndDeletedFalse(cleanUrlTitle)) {
            throw messageResolver.preconditionFailed("blog.clean.url.title.exists", cleanUrlTitle);
        }

        var entity = blogRequestMapper.toEntity(blogRequest);
        
        if (blogRequest.getImageUrlId() != null) {
            var image = imageUrlRepository.findByIdAndDeletedFalse(blogRequest.getImageUrlId())
                    .orElseThrow(() -> messageResolver.notFound("image.url.not.found", blogRequest.getImageUrlId()));
            entity.setImageUrl(image);
        }

        if (blogRequest.getVideoUrlId() != null) {
            var video = videoUrlRepository.findByIdAndNotDeleted(blogRequest.getVideoUrlId())
                    .orElseThrow(() -> messageResolver.notFound("video.url.not.found", blogRequest.getVideoUrlId()));
            entity.setVideoUrl(video);
        }

        var savedEntity = blogRepository.save(entity);
        var response = new ImageUrlPost201Response();
        response.setId(savedEntity.getId());
        log.info("Blog post created with id: {}", savedEntity.getId());
        return response;
    }

    private BlogEntity getEntityById(Long id) {
        return blogRepository.findByIdAndNotDeleted(id)
            .orElseThrow(() -> messageResolver.notFound("blog.post.not.found", id));
    }

    private String processCleanUrlTitle(BlogRequest blogRequest) {
        String titleToProcess = blogRequest.getCleanUrlTitle();
        if (titleToProcess == null || titleToProcess.isBlank()) {
            titleToProcess = blogRequest.getTitleEng();
        }

        if (titleToProcess == null) {
             return "";
        }

        String normalized = Normalizer.normalize(titleToProcess, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noAccents = pattern.matcher(normalized).replaceAll("");

        String noSpecialChars = noAccents.replaceAll("[^a-zA-Z0-9\\s-]", "");
        String withHyphens = noSpecialChars.trim().replaceAll("\\s+", "-");
        String lowerCase = withHyphens.toLowerCase();

        if (lowerCase.length() > 30) {
            lowerCase = lowerCase.substring(0, 30);
             if (lowerCase.endsWith("-")) {
                lowerCase = lowerCase.substring(0, lowerCase.length() - 1);
            }
        }

        return lowerCase;
    }

}
