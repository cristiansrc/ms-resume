package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEducationRepository extends JpaRepository<EducationEntity, Long> {

    @Query("SELECT e FROM EducationEntity e WHERE e.id = :id AND e.deleted = false")
    Optional<EducationEntity> findByIdAndDeletedFalse(@Param("id") Long identifier);

    @Query("SELECT e FROM EducationEntity e WHERE e.deleted = false")
    List<EducationEntity> findAllByDeletedFalse();
}
