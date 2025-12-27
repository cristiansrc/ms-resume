package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IHomeRepository extends JpaRepository<HomeEntity, Long> {
    boolean existsByImageUrlIdAndDeletedFalse(Long imageId);

    @Query("SELECT COUNT(h) > 0 FROM HomeEntity h JOIN h.homeLabelRelations r JOIN r.label l WHERE l.id = :labelId AND h.deleted = false")
    boolean existsByLabelIdAndDeletedFalse(@Param("labelId") Long labelId);
}
