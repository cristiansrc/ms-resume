package com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerCvIn {
    @JsonProperty("CV")
    private InfoCv cv;
    private Design design;
    private Locale locale;
}
