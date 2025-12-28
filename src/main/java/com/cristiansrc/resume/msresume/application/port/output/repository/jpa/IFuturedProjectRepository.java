package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.FuturedProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IFuturedProjectRepository extends JpaRepository<FuturedProjectEntity, Long> {
    @Query("SELECT f FROM FuturedProjectEntity f WHERE f.id = :id AND f.deleted = false")
    Optional<FuturedProjectEntity> findByIdAndDeletedFalse(@Param("id") Long identifier);

    @Query("SELECT f FROM FuturedProjectEntity f WHERE f.deleted = false")
    List<FuturedProjectEntity> findAllByDeletedFalse();

    boolean existsByImageUrlIdAndDeletedFalse(Long imageUrlId);

    boolean existsByImageListUrlIdAndDeletedFalse(Long imageListUrlId);
}
