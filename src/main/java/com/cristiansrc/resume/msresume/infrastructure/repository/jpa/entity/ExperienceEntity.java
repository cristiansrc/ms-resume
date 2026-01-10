package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.converter.LocalDateStringConverter;
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
    @Convert(converter = LocalDateStringConverter.class)
    private LocalDate yearStart;

    @Column(name = "year_end", nullable = false)
    @NotNull
    @Convert(converter = LocalDateStringConverter.class)
    private LocalDate yearEnd;

    @Column(nullable = false)
    @NotNull
    private String company;

    @Column(name = "company_eng", nullable = false)
    @NotNull
    private String companyEng;

    @Column(nullable = false)
    @NotNull
    private String position;

    @Column(name = "position_eng", nullable = false)
    @NotNull
    private String positionEng;

    @Column(nullable = false)
    @NotNull
    private String location;

    @Column(name = "location_eng", nullable = false)
    @NotNull
    private String locationEng;

    @Column(nullable = false)
    @NotNull
    private String summary;

    @Column(name = "summary_eng", nullable = false)
    @NotNull
    private String summaryEng;

    @Column(name = "summary_pdf", nullable = false)
    @NotNull
    private String summaryPdf;

    @Column(name = "summary_pdf_eng", nullable = false)
    @NotNull
    private String summaryPdfEng;

    @Column(name = "description_items_pdf", nullable = false)
    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> descriptionItems;

    @Column(name = "description_items_pdf_eng", nullable = false)
    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> descriptionItemsEng;

    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceSkillEntity> experienceSkills;
}
