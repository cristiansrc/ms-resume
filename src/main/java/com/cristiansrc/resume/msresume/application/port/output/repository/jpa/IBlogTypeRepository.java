package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BlogTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IBlogTypeRepository extends JpaRepository<BlogTypeEntity, Long> {

    @Query("SELECT b FROM BlogTypeEntity b WHERE b.id = :id AND b.deleted = false")
    Optional<BlogTypeEntity> findByIdAndDeletedFalse(@Param("id") Long identifier);

    @Query("SELECT b FROM BlogTypeEntity b WHERE b.deleted = false")
    List<BlogTypeEntity> findAllByDeletedFalse();
}
