package com.cristiansrc.resume.msresume.infrastructure.client.rendercv.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerCvOut {
    @JsonProperty("pdf_base64")
    private String pdfBase;
}
