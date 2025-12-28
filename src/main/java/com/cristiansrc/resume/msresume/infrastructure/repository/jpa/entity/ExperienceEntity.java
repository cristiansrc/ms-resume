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

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceSkillEntity> experienceSkills;
}
