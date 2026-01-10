package com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Education {
    private String institution;
    private String area;
    private String degree;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    private String location;
    private String summary;
    private List<String> highlights;
}
