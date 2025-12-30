package com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Experience {
    private String company;
    private String position;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    private String location;
    private String summary;
    private List<String> highlights;
}
