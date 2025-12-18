package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.VideoUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IVideoUrlRepository extends JpaRepository<VideoUrlEntity, Long> {
    @Query("SELECT vu FROM VideoUrlEntity vu WHERE vu.id = :id AND vu.deleted = false")
    Optional<VideoUrlEntity> findByIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT vu FROM VideoUrlEntity vu WHERE vu.deleted = false")
    List<VideoUrlEntity> findAllNotDeleted();
}
