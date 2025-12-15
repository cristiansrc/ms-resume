package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "experience")
public class ExperienceEntity extends AuditBasicEntity {

    @Column(name = "year_start", nullable = false)
    @NotNull
    private LocalDate yearStart;

    @Column(name = "year_end", nullable = false)
    @NotNull
    private LocalDate yearEnd;

    @Column(nullable = false)
    @NotNull
    private String company;

    @Column(nullable = false)
    @NotNull
    private String description;

    @Column(name = "description_eng", nullable = false)
    @NotNull
    private String descriptionEng;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="experience_skill",
            joinColumns=@JoinColumn(name="experience_id"),
            inverseJoinColumns=@JoinColumn(name="skill_son_id")
    )
    private List<SkillSonEntity> skillSons;
}
