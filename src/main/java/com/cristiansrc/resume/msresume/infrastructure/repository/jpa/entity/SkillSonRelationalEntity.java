package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
    name = "skill_son_relational",
    uniqueConstraints = @UniqueConstraint(columnNames = {"skill_id", "function_son_id"})
)
public class SkillSonRelationalEntity extends AuditBasicEntity {

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private SkillEntity skill;

    @ManyToOne
    @JoinColumn(name = "function_son_id", nullable = false)
    private SkillSonEntity skillSon;
}
