package com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Education {
    private String institution;
    private String area;
    private String degree;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    private String location;
    private String summary;
    private List<String> highlights;
}
