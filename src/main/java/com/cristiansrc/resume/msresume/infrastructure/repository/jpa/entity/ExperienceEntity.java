package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private String position;

    @Column(name = "position_eng", nullable = false)
    @NotNull
    private String positionEng;

    @Column(nullable = false)
    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> description;

    @Column(name = "description_eng", nullable = false)
    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> descriptionEng;

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceSkillEntity> experienceSkills;
}
