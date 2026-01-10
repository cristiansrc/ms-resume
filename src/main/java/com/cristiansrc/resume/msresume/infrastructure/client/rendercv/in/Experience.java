package com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Experience {
    private String company;
    private String position;

    @JsonProperty("start_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate startDate;

    @JsonProperty("end_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate endDate;

    private String location;
    private String summary;
    private List<String> highlights;
}
