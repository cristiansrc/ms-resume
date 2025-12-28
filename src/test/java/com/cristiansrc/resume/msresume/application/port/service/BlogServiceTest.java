package com.cristiansrc.resume.msresume.application.port.service;

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
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogTypeEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.VideoUrlEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BlogServiceTest {

    @Mock
    private IBlogRepository blogRepository;

    @Mock
    private IBlogResponseMapper blogResponseMapper;

    @Mock
    private MessageResolver messageResolver;

    @Mock
    private IBlogRequestMapper blogRequestMapper;

    @Mock
    private IImageUrlRepository imageUrlRepository;

    @Mock
    private IVideoUrlRepository videoUrlRepository;

    @Mock
    private IBlogTypeRepository blogTypeRepository;

    @Mock
    private IS3Service s3Service;

    @InjectMocks
    private BlogService blogService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void blogGet() {
        Page<BlogEntity> page = new PageImpl<>(Collections.singletonList(new BlogEntity()));
        when(blogRepository.findByTitleContainingIgnoreCaseAndSort(any(), any(PageRequest.class))).thenReturn(page);
        when(blogResponseMapper.toPageResponse(page, s3Service)).thenReturn(new BlogPageResponse());

        BlogPageResponse response = blogService.blogGet(0, 10, "id,desc");

        assertNotNull(response);
    }

    @Test
    void blogGet_defaultParams() {
        Page<BlogEntity> page = new PageImpl<>(Collections.singletonList(new BlogEntity()));
        when(blogRepository.findByTitleContainingIgnoreCaseAndSort(any(), any(PageRequest.class))).thenReturn(page);
        when(blogResponseMapper.toPageResponse(page, s3Service)).thenReturn(new BlogPageResponse());

        BlogPageResponse response = blogService.blogGet(null, null, null);

        assertNotNull(response);
    }

    @Test
    void blogIdDelete() {
        BlogEntity entity = new BlogEntity();
        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(blogRepository.save(any())).thenReturn(entity);

        blogService.blogIdDelete(1L);

        assertTrue(entity.getDeleted());
        verify(blogRepository).save(entity);
    }

    @Test
    void blogIdGet() {
        BlogEntity entity = new BlogEntity();
        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(blogResponseMapper.toResponse(entity, s3Service)).thenReturn(new BlogResponse());

        BlogResponse response = blogService.blogIdGet(1L);

        assertNotNull(response);
    }

    @Test
    void blogIdPut_success() {
        BlogEntity entity = new BlogEntity();
        entity.setCleanUrlTitle("old-title");
        BlogRequest request = new BlogRequest();
        request.setCleanUrlTitle("new-title");
        request.setImageUrlId(1L);
        request.setVideoUrlId(1L);
        request.setBlogTypeId(1L);

        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse("new-title")).thenReturn(false);
        doNothing().when(blogRequestMapper).updateEntityFromBlogRequest(any(), any());
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new ImageUrlEntity()));
        when(videoUrlRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(new VideoUrlEntity()));
        when(blogTypeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new BlogTypeEntity()));
        when(blogRepository.save(any())).thenReturn(entity);

        blogService.blogIdPut(1L, request);

        verify(blogRepository).save(entity);
    }

    @Test
    void blogIdPut_nullRelations() {
        BlogEntity entity = new BlogEntity();
        entity.setCleanUrlTitle("old-title");
        BlogRequest request = new BlogRequest();
        request.setCleanUrlTitle("new-title");
        request.setImageUrlId(null);
        request.setVideoUrlId(null);
        request.setBlogTypeId(null);

        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse("new-title")).thenReturn(false);
        doNothing().when(blogRequestMapper).updateEntityFromBlogRequest(any(), any());
        when(blogRepository.save(any())).thenReturn(entity);

        blogService.blogIdPut(1L, request);

        assertNull(entity.getImageUrl());
        assertNull(entity.getVideoUrl());
        assertNull(entity.getBlogType());
        verify(blogRepository).save(entity);
    }

    @Test
    void blogIdPut_notFoundRelations() {
        BlogEntity entity = new BlogEntity();
        entity.setCleanUrlTitle("old-title");
        BlogRequest request = new BlogRequest();
        request.setCleanUrlTitle("new-title");
        request.setImageUrlId(1L);

        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entity));
        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse("new-title")).thenReturn(false);
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound("image.url.not.found", 1L)).thenThrow(new RuntimeException("Image not found"));

        assertThrows(RuntimeException.class, () -> blogService.blogIdPut(1L, request));
    }

    @Test
    void blogIdPut_shouldThrowPreconditionFailed_whenCleanUrlTitleExists() {
        final String existingTitle = "existing-title";
        BlogEntity entityToUpdate = new BlogEntity();
        entityToUpdate.setCleanUrlTitle("old-title");

        BlogRequest request = new BlogRequest();
        request.setCleanUrlTitle(existingTitle);

        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(entityToUpdate));
        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse(existingTitle)).thenReturn(true);
        when(messageResolver.preconditionFailed("blog.clean.url.title.exists", existingTitle))
                .thenThrow(new RuntimeException("Precondition Failed"));

        assertThrows(RuntimeException.class, () -> blogService.blogIdPut(1L, request));
    }

    @Test
    void blogPost_success() {
        BlogEntity entity = new BlogEntity();
        BlogRequest request = new BlogRequest();
        request.setCleanUrlTitle("new-blog");
        request.setImageUrlId(1L);
        request.setVideoUrlId(1L);
        request.setBlogTypeId(1L);

        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse("new-blog")).thenReturn(false);
        when(blogRequestMapper.toEntity(request)).thenReturn(entity);
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new ImageUrlEntity()));
        when(videoUrlRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(new VideoUrlEntity()));
        when(blogTypeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(new BlogTypeEntity()));
        when(blogRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = blogService.blogPost(request);

        assertNotNull(response);
    }

    @Test
    void blogPost_nullRelations() {
        BlogEntity entity = new BlogEntity();
        BlogRequest request = new BlogRequest();
        request.setCleanUrlTitle("new-blog");
        request.setImageUrlId(null);
        request.setVideoUrlId(null);
        request.setBlogTypeId(null);

        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse("new-blog")).thenReturn(false);
        when(blogRequestMapper.toEntity(request)).thenReturn(entity);
        when(blogRepository.save(any())).thenReturn(entity);

        ImageUrlPost201Response response = blogService.blogPost(request);

        assertNotNull(response);
        assertNull(entity.getImageUrl());
        assertNull(entity.getVideoUrl());
        assertNull(entity.getBlogType());
    }

    @Test
    void blogPost_notFoundRelations() {
        BlogRequest request = new BlogRequest();
        request.setCleanUrlTitle("new-blog");
        request.setImageUrlId(1L);

        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse("new-blog")).thenReturn(false);
        when(blogRequestMapper.toEntity(request)).thenReturn(new BlogEntity());
        when(imageUrlRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound("image.url.not.found", 1L)).thenThrow(new RuntimeException("Image not found"));

        assertThrows(RuntimeException.class, () -> blogService.blogPost(request));
    }

    @Test
    void blogPost_shouldGenerateCleanUrlTitle_whenNotProvided() {
        BlogEntity entity = new BlogEntity();
        BlogRequest request = new BlogRequest();
        request.setTitleEng("My New Blog Post!");

        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse("my-new-blog-post")).thenReturn(false);
        when(blogRequestMapper.toEntity(request)).thenReturn(entity);
        when(blogRepository.save(any())).thenReturn(entity);

        blogService.blogPost(request);

        assertEquals("my-new-blog-post", request.getCleanUrlTitle());
    }

    @Test
    void blogPost_shouldThrowPreconditionFailed_whenCleanUrlTitleExists() {
        BlogRequest request = new BlogRequest();
        request.setCleanUrlTitle("existing-blog");

        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse("existing-blog")).thenReturn(true);
        when(messageResolver.preconditionFailed("blog.clean.url.title.exists", "existing-blog"))
                .thenThrow(new RuntimeException("Precondition Failed"));

        assertThrows(RuntimeException.class, () -> blogService.blogPost(request));
    }

    @Test
    void processCleanUrlTitle_empty() {
        BlogRequest request = new BlogRequest();
        request.setTitleEng("");
        request.setCleanUrlTitle("");
        
        // We need to mock the flow to reach processCleanUrlTitle indirectly via blogPost
        // But since it returns empty string and then sets it, we can check if it handles it gracefully
        // However, processCleanUrlTitle is private. We test its effect via blogPost.
        
        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse("")).thenReturn(false);
        when(blogRequestMapper.toEntity(request)).thenReturn(new BlogEntity());
        when(blogRepository.save(any())).thenReturn(new BlogEntity());

        blogService.blogPost(request);
        
        assertEquals("", request.getCleanUrlTitle());
    }

    @Test
    void processCleanUrlTitle_longTitle() {
        BlogRequest request = new BlogRequest();
        String longTitle = "This is a very long title that should be truncated because it is way too long for the url";
        request.setTitleEng(longTitle);

        when(blogRepository.existsByCleanUrlTitleAndDeletedFalse(any())).thenReturn(false);
        when(blogRequestMapper.toEntity(request)).thenReturn(new BlogEntity());
        when(blogRepository.save(any())).thenReturn(new BlogEntity());

        blogService.blogPost(request);

        String cleanTitle = request.getCleanUrlTitle();
        assertTrue(cleanTitle.length() <= 30);
        assertEquals("this-is-a-very-long-title-that", cleanTitle);
    }

    @Test
    void getEntityById_notFound() {
        when(blogRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.empty());
        when(messageResolver.notFound(any(), any())).thenThrow(new RuntimeException("blog.post.not.found"));

        assertThrows(RuntimeException.class, () -> blogService.blogIdGet(1L));
    }
}
