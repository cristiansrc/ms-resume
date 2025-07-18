package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface IExperienceRepository extends JpaRepository<ExperienceEntity, Long> {

    @Query("SELECT e FROM ExperienceEntity e WHERE e.id = :id AND e.deleted = false")
    Optional<ExperienceEntity> findByIdAndDeletedFalse(@Param("id") Long id);

    @Query("SELECT e FROM ExperienceEntity e WHERE e.deleted = false")
    List<ExperienceEntity> findAllByDeletedFalse();
}
