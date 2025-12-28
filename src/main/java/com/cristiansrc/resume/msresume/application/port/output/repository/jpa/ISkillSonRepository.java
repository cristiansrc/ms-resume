package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ISkillSonRepository extends JpaRepository<SkillSonEntity, Long> {

    @Query("SELECT s FROM SkillSonEntity s WHERE s.id = :id AND s.deleted = false")
    Optional<SkillSonEntity> findByIdAndDeletedFalse(@Param("id") Long identifier);

    @Query("SELECT s FROM SkillSonEntity s WHERE s.deleted = false")
    List<SkillSonEntity> findAllByDeletedFalse();
}
