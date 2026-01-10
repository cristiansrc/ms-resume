package com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocialNetwork {
    private String network;
    private String username;
}
