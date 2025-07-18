package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IBlogService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBlogRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogService implements IBlogService {

    private final IBlogRepository blogRepository;
    private final IBlogResponseMapper blogResponseMapper;
    private final MessageResolver messageResolver;
    private final IBlogRequestMapper blogRequestMapper;

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<BlogPageResponse> blogGet(Integer page, Integer size, String sortDirection){
        log.debug("Fetching blog posts with page: {}, size: {}, sort: {}", page, size, sortDirection);
        sortDirection = sortDirection == null ? "DESC" : sortDirection.toUpperCase();
        size = size == null ? 10 : size;
        var sort = Sort.by(Sort.Direction.fromString(sortDirection), "created");
        var pageable = PageRequest.of(page, size, sort);
        var entityPage = blogRepository.findByTitleContainingIgnoreCaseAndSort(null, pageable);
        var models = blogResponseMapper.toPageResponse(entityPage);
        log.debug("Fetched {} blog posts", models.getContent().size());
        return ResponseEntity.ok(models);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> blogIdDelete(Long id) {
        log.info("Deleting blog post with id: {}", id);
        var entity = getEntityById(id);
        entity.setDeleted(true);
        blogRepository.save(entity);
        log.info("Blog post with id: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<BlogResponse> blogIdGet(Long id) {
        log.debug("Fetching blog post with id: {}", id);
        var entity = getEntityById(id);
        var response = blogResponseMapper.toResponse(entity);
        log.debug("Fetched blog post with id: {}", id);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> blogIdPut(Long id, BlogRequest blogRequest) {
        log.info("Updating blog post with id: {}", id);
        var entity = getEntityById(id);
        blogRequestMapper.updateEntityFromBlogRequest(blogRequest, entity);
        blogRepository.save(entity);
        log.info("Blog post with id: {} updated successfully", id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Override
    public ResponseEntity<ImageUrlPost201Response> blogPost(BlogRequest blogRequest) {
        log.info("Creating new blog post");
        var entity = blogRequestMapper.toEntity(blogRequest);
        var savedEntity = blogRepository.save(entity);
        var response = new ImageUrlPost201Response();
        response.setId(savedEntity.getId());
        log.info("Blog post created with id: {}", savedEntity.getId());
        return ResponseEntity.status(201).body(response);
    }

    private BlogEntity getEntityById(Long id) {
        return blogRepository.findByIdAndNotDeleted(id)
            .orElseThrow(() -> messageResolver.notFound("blog.post.not.found", id));
    }

}
