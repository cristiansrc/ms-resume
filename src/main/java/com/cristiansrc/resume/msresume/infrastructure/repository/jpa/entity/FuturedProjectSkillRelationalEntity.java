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
        name = "futured_project_skill_relational",
        uniqueConstraints = @UniqueConstraint(columnNames = {"futured_project_id", "skill_son_id"})
)
public class FuturedProjectSkillRelationalEntity extends AuditBasicEntity {

    @ManyToOne
    @JoinColumn(name = "futured_project_id", nullable = false)
    private FuturedProjectEntity futuredProject;

    @ManyToOne
    @JoinColumn(name = "skill_son_id", nullable = false)
    private SkillSonEntity skillSon;
}
