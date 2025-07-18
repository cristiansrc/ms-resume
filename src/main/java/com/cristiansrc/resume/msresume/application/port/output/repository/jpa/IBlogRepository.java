package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IBlogRepository extends JpaRepository<BlogEntity, Long> {

    @Query("SELECT b FROM BlogEntity b WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND b.deleted = false")
    Page<BlogEntity> findByTitleContainingIgnoreCaseAndSort(String title, Pageable pageable);

    @Query("SELECT b FROM BlogEntity b WHERE b.id = :id AND b.deleted = false")
    Optional<BlogEntity> findByIdAndNotDeleted(@Param("id") Long id);
}
