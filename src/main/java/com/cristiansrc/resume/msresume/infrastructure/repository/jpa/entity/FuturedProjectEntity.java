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
@Table(name = "futured_project")
public class FuturedProjectEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "experience_id", nullable = false)
    private ExperienceEntity experience;

    @Column(name = "description_short", nullable = false)
    @NotNull
    private String descriptionShort;

    @Column(nullable = false)
    @NotNull
    private String description;

    @Column(nullable = false)
    @NotNull
    private String imageListUrl;

    @Column(nullable = false)
    @NotNull
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name="futured_project_skill_relational",
            joinColumns=@JoinColumn(name="futured_project_id"),
            inverseJoinColumns=@JoinColumn(name="skill_son_id")
    )
    private List<SkillSonEntity> skillSons;
}
