package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IBlogService;
import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBlogRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBlogTypeRepository;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class BlogService implements IBlogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogService.class);
    private static final String BLOG_CLEAN_URL_TITLE_EXISTS = "blog.clean.url.title.exists";
    private static final String IMAGE_URL_NOT_FOUND = "image.url.not.found";
    private static final String VIDEO_URL_NOT_FOUND = "video.url.not.found";
    private static final String BLOG_TYPE_NOT_FOUND = "blog.type.not.found";
    private static final Pattern DIACRITICAL_MARKS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-zA-Z0-9\\s-]");
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    private final IBlogRepository blogRepo;
    private final IBlogResponseMapper responseMapper;
    private final MessageResolver messageResolver;
    private final IBlogRequestMapper requestMapper;
    private final IImageUrlRepository imageUrlRepo;
    private final IVideoUrlRepository videoUrlRepo;
    private final IBlogTypeRepository blogTypeRepo;
    private final IS3Service s3Service;

    @Transactional(readOnly = true)
    @Override
    public BlogPageResponse blogGet(final Integer page, final Integer size, final String sortParam) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetching blog posts with page: {}, size: {}, sort: {}", page, size, sortParam);
        }

        final int pageNumber = Optional.ofNullable(page).filter(p -> p >= 0).orElse(0);
        final int pageSize = Optional.ofNullable(size).filter(s -> s >= 1).orElse(10);
        final String sortString = Optional.ofNullable(sortParam).filter(s -> !s.isBlank()).orElse("id,desc");

        final String[] sortParts = sortString.split(",");
        final String property = sortParts[0];
        final Sort.Direction direction = (sortParts.length > 1) ? Sort.Direction.fromString(sortParts[1]) : Sort.Direction.DESC;

        final Sort sort = Sort.by(direction, property);
        final Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        final Page<BlogEntity> entityPage = blogRepo.findByTitleContainingIgnoreCaseAndSort(null, pageable);
        final BlogPageResponse models = responseMapper.toPageResponse(entityPage, s3Service);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetched {} blog posts", models.getContent().size());
        }
        return models;
    }

    @Transactional
    @Override
    public void blogIdDelete(final Long identifier) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleting blog post with id: {}", identifier);
        }
        final BlogEntity entity = getEntityById(identifier);
        entity.setDeleted(true);
        blogRepo.save(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Blog post with id: {} deleted successfully", identifier);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public BlogResponse blogIdGet(final Long identifier) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetching blog post with id: {}", identifier);
        }
        final BlogEntity entity = getEntityById(identifier);
        final BlogResponse response = responseMapper.toResponse(entity, s3Service);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetched blog post with id: {}", identifier);
        }
        return response;
    }

    @Transactional
    @Override
    public void blogIdPut(final Long identifier, final BlogRequest blogRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updating blog post with id: {}", identifier);
        }
        final BlogEntity entity = getEntityById(identifier);

        final String cleanUrlTitle = processCleanUrlTitle(blogRequest);
        
        if (!entity.getCleanUrlTitle().equals(cleanUrlTitle) && blogRepo.existsByCleanUrlTitleAndDeletedFalse(cleanUrlTitle)) {
            throw messageResolver.preconditionFailed(BLOG_CLEAN_URL_TITLE_EXISTS, cleanUrlTitle);
        }
        
        blogRequest.setCleanUrlTitle(cleanUrlTitle);
        requestMapper.updateEntityFromBlogRequest(blogRequest, entity);
        
        updateRelations(entity, blogRequest);

        blogRepo.save(entity);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Blog post with id: {} updated successfully", identifier);
        }
    }

    @Transactional
    @Override
    public ImageUrlPost201Response blogPost(final BlogRequest blogRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Creating new blog post");
        }

        final String cleanUrlTitle = processCleanUrlTitle(blogRequest);
        blogRequest.setCleanUrlTitle(cleanUrlTitle);

        if (blogRepo.existsByCleanUrlTitleAndDeletedFalse(cleanUrlTitle)) {
            throw messageResolver.preconditionFailed(BLOG_CLEAN_URL_TITLE_EXISTS, cleanUrlTitle);
        }

        final BlogEntity entity = requestMapper.toEntity(blogRequest);
        
        updateRelations(entity, blogRequest);

        final BlogEntity savedEntity = blogRepo.save(entity);
        final ImageUrlPost201Response response = new ImageUrlPost201Response();
        response.setId(savedEntity.getId());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Blog post created with id: {}", savedEntity.getId());
        }
        return response;
    }

    private void updateRelations(final BlogEntity entity, final BlogRequest blogRequest) {
        Optional.ofNullable(blogRequest.getImageUrlId())
                .ifPresentOrElse(
                        imgId -> entity.setImageUrl(imageUrlRepo.findByIdAndDeletedFalse(imgId)
                                .orElseThrow(() -> messageResolver.notFound(IMAGE_URL_NOT_FOUND, imgId))),
                        () -> {
                            if (entity.getId() != null) entity.setImageUrl(null);
                        }
                );

        Optional.ofNullable(blogRequest.getVideoUrlId())
                .ifPresentOrElse(
                        vidId -> entity.setVideoUrl(videoUrlRepo.findByIdAndNotDeleted(vidId)
                                .orElseThrow(() -> messageResolver.notFound(VIDEO_URL_NOT_FOUND, vidId))),
                        () -> {
                            if (entity.getId() != null) entity.setVideoUrl(null);
                        }
                );

        Optional.ofNullable(blogRequest.getBlogTypeId())
                .ifPresentOrElse(
                        typeId -> entity.setBlogType(blogTypeRepo.findByIdAndDeletedFalse(typeId)
                                .orElseThrow(() -> messageResolver.notFound(BLOG_TYPE_NOT_FOUND, typeId))),
                        () -> {
                            if (entity.getId() != null) entity.setBlogType(null);
                        }
                );
    }

    private BlogEntity getEntityById(final Long identifier) {
        return blogRepo.findByIdAndNotDeleted(identifier)
            .orElseThrow(() -> messageResolver.notFound("blog.post.not.found", identifier));
    }

    private String processCleanUrlTitle(final BlogRequest blogRequest) {
        final String titleToProcess = Optional.ofNullable(blogRequest.getCleanUrlTitle())
                .filter(t -> !t.isBlank())
                .or(() -> Optional.ofNullable(blogRequest.getTitleEng()))
                .orElse("");

        if (titleToProcess.isBlank()) {
             return "";
        }

        final String normalized = Normalizer.normalize(titleToProcess, Normalizer.Form.NFD);
        final String noAccents = DIACRITICAL_MARKS.matcher(normalized).replaceAll("");

        final String noSpecialChars = NON_ALPHANUMERIC.matcher(noAccents).replaceAll("");
        final String withHyphens = WHITESPACE.matcher(noSpecialChars.trim()).replaceAll("-");
        String lowerCase = withHyphens.toLowerCase();

        if (lowerCase.length() > 30) {
            lowerCase = lowerCase.substring(0, 30);
        }

        return lowerCase.endsWith("-") ? lowerCase.substring(0, lowerCase.length() - 1) : lowerCase;
    }

}
