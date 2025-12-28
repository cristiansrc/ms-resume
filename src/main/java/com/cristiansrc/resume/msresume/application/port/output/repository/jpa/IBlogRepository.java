package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IBlogRepository extends JpaRepository<BlogEntity, Long> {

    @Query("SELECT b FROM BlogEntity b WHERE (:title IS NULL OR LOWER(CAST(b.title AS string)) LIKE LOWER(CONCAT('%', CAST(:title AS string), '%'))) AND b.deleted = false")
    Page<BlogEntity> findByTitleContainingIgnoreCaseAndSort(@Param("title") String title, Pageable pageable);

    @Query("SELECT b FROM BlogEntity b WHERE b.id = :id AND b.deleted = false")
    Optional<BlogEntity> findByIdAndNotDeleted(@Param("id") Long identifier);

    boolean existsByVideoUrlIdAndDeletedFalse(Long videoUrlId);

    boolean existsByCleanUrlTitleAndDeletedFalse(String cleanUrlTitle);
}
