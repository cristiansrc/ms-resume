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
@Table(name = "education")
public class EducationEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String institution;

    @Column(nullable = false)
    @NotNull
    private String area;

    @Column(name = "area_eng", nullable = false)
    @NotNull
    private String areaEng;

    @Column(nullable = false)
    @NotNull
    private String degree;

    @Column(name = "degree_eng", nullable = false)
    @NotNull
    private String degreeEng;

    @Column(name = "start_date", nullable = false)
    @NotNull
    @Convert(converter = LocalDateStringConverter.class)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @NotNull
    @Convert(converter = LocalDateStringConverter.class)
    private LocalDate endDate;

    @Column(nullable = false)
    @NotNull
    private String location;

    @Column(name = "location_eng", nullable = false)
    @NotNull
    private String locationEng;

    @Column(nullable = false)
    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> highlights;

    @Column(name = "highlights_eng", nullable = false)
    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> highlightsEng;
}
