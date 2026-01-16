package com.cristiansrc.resume.msresume.infrastructure.constants;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CvConstants {

    @Value("${config.rendercv.website}")
    public String webSite;

    @Value("${config.rendercv.theme}")
    public String theme;

    @Value("${config.linkedin.url}")
    public String linkedinUrl;

    @Value("${config.github.url}")
    public String githubUrl;

    @Value("${config.x.url}")
    public String xUrl;

    @Value("${config.instagram.url}")
    public String instagramUrl;

    @Value("${config.rendercv.debug.enabled:false}")
    public boolean debugEnabled;

    @Value("${config.rendercv.debug.path:/tmp/}")
    public String debugPath;

    @Value("${config.rendercv.debug.filename:rendercv-debug.json}")
    public String debugFilename;

    @Value("${config.rendercv.filename.spanish:CristhiamReinaLatinCv.pdf}")
    public String filenameSpanish;

    @Value("${config.rendercv.filename.english:CristhiamReinaEnglishCv.pdf}")
    public String filenameEnglish;
}
