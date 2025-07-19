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
@Table(name = "skill_type")
public class SkillTypeEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="skill_type_relational",
            joinColumns=@JoinColumn(name="skill_type_id"),
            inverseJoinColumns=@JoinColumn(name="skill_id")
    )
    private List<SkillEntity> skills;

}
