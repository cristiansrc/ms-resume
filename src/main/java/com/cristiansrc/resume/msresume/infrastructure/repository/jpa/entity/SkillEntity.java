package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "skill")
public class SkillEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String name;

    @ManyToMany
    @JoinTable(
            name="skill_son_relational",
            joinColumns=@JoinColumn(name="skill_id"),
            inverseJoinColumns=@JoinColumn(name="skill_son_id")
    )
    private List<SkillSonEntity> skillSons;
}
