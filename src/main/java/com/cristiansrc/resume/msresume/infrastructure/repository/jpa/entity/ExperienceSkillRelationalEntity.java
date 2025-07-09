package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "experience_skill",
        uniqueConstraints = @UniqueConstraint(columnNames = {"experience_id", "skill_son_id"})
)
public class ExperienceSkillRelationalEntity extends AuditBasicEntity {

    @ManyToOne
    @JoinColumn(name = "experience_id", nullable = false)
    private ExperienceEntity experience;

    @ManyToOne
    @JoinColumn(name = "skill_son_id", nullable = false)
    private SkillSonEntity skill;
}
