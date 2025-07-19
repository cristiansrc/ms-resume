package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ImageUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IImageUrlRepository extends JpaRepository<ImageUrlEntity, Long> {

    @Query("SELECT i FROM ImageUrlEntity i WHERE i.id = :id AND i.deleted = false")
    Optional<ImageUrlEntity> findByIdAndDeletedFalse(@Param("id") Long id);

    @Query("SELECT i FROM ImageUrlEntity i WHERE i.deleted = false")
    List<ImageUrlEntity> findAllByDeletedFalse();
}
