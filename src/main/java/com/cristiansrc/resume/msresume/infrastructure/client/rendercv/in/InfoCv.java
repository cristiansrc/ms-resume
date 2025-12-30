package com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class InfoCv {
    private String name;
    private String headline;
    private String location;
    private String email;
    private String photo;
    private String phone;
    private String website;

    @JsonProperty("social_networks")
    private List<SocialNetwork> socialNetworks;

    private Map<String, Object> sections = new LinkedHashMap<>();
    private Design design;
    private Locale locale;

    public void addSection(String key, Object content) {
        this.sections.put(key, content);
    }
}
