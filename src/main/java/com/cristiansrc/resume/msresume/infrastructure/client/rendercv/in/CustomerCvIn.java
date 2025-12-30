package com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in;

import lombok.Data;

@Data
public class CustomerCvIn {
    private InfoCv cv;
    private Design design;
    private Locale locale;
}
