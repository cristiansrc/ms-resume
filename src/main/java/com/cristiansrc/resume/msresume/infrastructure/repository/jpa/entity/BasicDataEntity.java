package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "basic_data")
public class BasicDataEntity extends AuditBasicEntity {

    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "others_name", nullable = false)
    @NotNull
    private String othersName;

    @Column(name = "first_surname", nullable = false)
    @NotNull
    private String firstSurName;

    @Column(name = "others_sur_name", nullable = false)
    @NotNull
    private String othersSurName;

    @Column(name = "date_birth", nullable = false)
    @NotNull
    private LocalDate dateBirth;

    @Column(nullable = false)
    @NotNull
    private String located;

    @Column(name = "located_eng", nullable = false)
    @NotNull
    private String locatedEng;

    @Column(name = "start_working_date", nullable = false)
    @NotNull
    private LocalDate startWorkingDate;

    @Column(nullable = false)
    @NotNull
    private String greeting;

    @Column(name = "greeting_eng", nullable = false)
    @NotNull
    private String greetingEng;

    @Column(nullable = false)
    @NotNull
    private String email;

    @Column(nullable = false)
    @NotNull
    private String instagram;

    @Column(nullable = false)
    @NotNull
    private String linkedin;

    @Column(nullable = false)
    @NotNull
    private String x;

    @Column(nullable = false)
    @NotNull
    private String github;

    @Column(nullable = false)
    @NotNull
    private String description;

    @Column(name = "description_eng", nullable = false)
    @NotNull
    private String descriptionEng;

}
