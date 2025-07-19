package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ISkillTypeRepository extends JpaRepository<SkillTypeEntity, Long> {

    @Query("SELECT st FROM SkillTypeEntity st WHERE st.id = :id AND st.deleted = false")
    Optional<SkillTypeEntity> findByIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT st FROM SkillTypeEntity st WHERE st.deleted = false")
    List<SkillTypeEntity> findAllNotDeleted();
}
