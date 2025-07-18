package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceSkillRelationalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExperienceSkillRelationalRepository extends JpaRepository<ExperienceSkillRelationalEntity, Long> {
}
