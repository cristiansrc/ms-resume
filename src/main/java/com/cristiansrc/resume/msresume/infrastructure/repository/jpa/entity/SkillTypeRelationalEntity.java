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
        name = "skill_type_relational",
        uniqueConstraints = @UniqueConstraint(columnNames = {"skill_id", "skill_type_id"})
)
public class SkillTypeRelationalEntity extends AuditBasicEntity {

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private SkillEntity skill;

    @ManyToOne
    @JoinColumn(name = "skill_type_id", nullable = false)
    private SkillTypeEntity skillType;
}
