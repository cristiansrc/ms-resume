package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.exception.RenderCvServiceException;
import com.cristiansrc.resume.msresume.application.port.interactor.*;
import com.cristiansrc.resume.msresume.application.port.output.client.IRenderCvClient;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBasicDataRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in.*;
import com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in.Locale;
import com.cristiansrc.resume.msresume.infrastructure.client.rendercv.out.CustomerCvOut;
import com.cristiansrc.resume.msresume.infrastructure.constants.InfoCvConstants;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.*;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBasicDataMapper;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IHomeMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicService implements IPublicService {

    private final IHomeRepository homeRepository;
    private final IBasicDataRepository basicDataRepository;
    private final ISkillTypeService skillTypeService;
    private final ISkillService skillService;
    private final IExperienceService experienceService;
    private final IEducationService educationService;
    private final IHomeMapper homeMapper;
    private final IBasicDataMapper basicDataMapper;
    private final IS3Service s3Service;
    private final IRenderCvClient renderCvClient;
    private final ObjectMapper objectMapper;

    @Value("${config.rendercv.website}")
    private String webSite;

    @Value("${config.rendercv.theme}")
    private String theme;

    @Value("${config.linkedin.url}")
    private String linkedinUrl;

    @Value("${config.github.url}")
    private String githubUrl;

    @Value("${config.x.url}")
    private String xUrl;

    @Value("${config.instagram.url}")
    private String instagramUrl;

    @Value("${config.rendercv.debug.enabled:false}")
    private boolean debugEnabled;

    @Value("${config.rendercv.debug.path:/tmp/}")
    private String debugPath;

    @Value("${config.rendercv.debug.filename:rendercv-debug.json}")
    private String debugFilename;

    @Transactional(readOnly = true)
    @Override
    public InfoPageResponse getInfoPage() {
        log.info("Fetching public info page data");

        var homeResponse = getHomeResponse();
        var basicDataResponse = getBasicDataResponse();
        var skillTypes = skillTypeService.skillTypeGet();
        var experiences = experienceService.experienceGet();
        var educations = educationService.educationGet();

        log.info("Fetched public info page data successfully");

        var infoPageResponse = new InfoPageResponse();
        infoPageResponse.setHome(homeResponse);
        infoPageResponse.setBasicData(basicDataResponse);
        infoPageResponse.setSkillTypes(skillTypes);
        infoPageResponse.setExperiences(experiences);
        infoPageResponse.setEducations(educations);

        return infoPageResponse;
    }

    @Override
    public Resource publicCurriculumGet(String language) {
        log.info("Fetching resume cv data for language: {}", language);

        var basicDataResponse = getBasicDataResponse();
        if (basicDataResponse == null) {
            log.warn("Basic data not found, cannot generate CV");
            throw new RenderCvServiceException("Basic data not found", null);
        }

        var infoCv = getInfoCv(basicDataResponse);
        var sections = getSections(basicDataResponse, language);
        
        infoCv.setSections(sections);

        var customerCvIn = new CustomerCvIn();
        customerCvIn.setCv(infoCv);
        customerCvIn.setDesign(Design.builder().theme(theme).build());
        customerCvIn.setLocale(Locale.builder().language(language).build());

        if (debugEnabled) {
            try {
                File directory = new File(debugPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                File file = new File(directory, debugFilename);
                objectMapper.writeValue(file, customerCvIn);
                log.info("JSON debug file written to: {}", file.getAbsolutePath());
            } catch (IOException e) {
                log.error("Error writing JSON debug file", e);
            }
        }

        var customerCvOut = renderCvClient.renderCv(customerCvIn);

        return Optional.ofNullable(customerCvOut)
                .map(CustomerCvOut::getPdfBase)
                .map(Base64.getDecoder()::decode)
                .map(ByteArrayResource::new)
                .orElseThrow(() -> new RenderCvServiceException("Failed to generate PDF content", null));
    }

    private InfoCv getInfoCv(BasicDataResponse basicDataResponse) {

        String fullName = Stream.of(
                        basicDataResponse.getFirstName(),
                        basicDataResponse.getOthersName(),
                        basicDataResponse.getFirstSurName(),
                        basicDataResponse.getOthersSurName())
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" "));

        return InfoCv.builder()
                .name(fullName)
                .location(basicDataResponse.getLocated())
                .email(basicDataResponse.getEmail())
                .website(webSite)
                .socialNetworks(getSocialNetworks(basicDataResponse))
                .build();
    }

    private List<SocialNetwork> getSocialNetworks(BasicDataResponse basicData) {
        List<SocialNetwork> socialNetworks = new ArrayList<>();

        addSocialNetworkIfPresent(
                socialNetworks,
                InfoCvConstants.NETWORK_GITHUB,
                basicData.getGithub(),
                githubUrl);

        addSocialNetworkIfPresent(
                socialNetworks,
                InfoCvConstants.NETWORK_LINKEDIN,
                basicData.getLinkedin(),
                linkedinUrl);

        addSocialNetworkIfPresent(
                socialNetworks,
                InfoCvConstants.NETWORK_X,
                basicData.getX(),
                xUrl);

        addSocialNetworkIfPresent(
                socialNetworks,
                InfoCvConstants.NETWORK_INSTAGRAM,
                basicData.getInstagram(),
                instagramUrl);

        return socialNetworks;
    }

    private void addSocialNetworkIfPresent(List<SocialNetwork> networks, String networkName, String username, String replaceUrl) {
        if (username != null && !username.isBlank() && !replaceUrl.isEmpty()) {
            networks.add(SocialNetwork.builder()
                    .network(networkName)
                    .username(username.replace(replaceUrl, "").replace("/", ""))
                    .build());
        }
    }

    private List<Education> getEducations(boolean isSpanish) {
        return educationService.educationGet().stream()
                .map(e -> Education.builder()
                        .institution(e.getInstitution())
                        .area(resolveText(isSpanish, e.getArea(), e.getAreaEng()))
                        .degree(resolveText(isSpanish, e.getDegree(), e.getDegreeEng()))
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .highlights(e.getHighlights())
                        .location(resolveText(isSpanish, e.getLocation(), e.getLocationEng()))
                        .build())
                .toList();
    }

    private List<Experience> getExperiences(boolean isSpanish) {
        return experienceService.experienceGet().stream()
                .map(e -> {

                    var highlights = isSpanish ? e.getDescriptionItemsPdf() : e.getDescriptionItemsPdfEng();

                    var stringSkills = e.getSkillSons().stream()
                            .map( s -> resolveText(isSpanish, s.getName(), s.getNameEng()))
                            .filter(name -> name != null && !name.isBlank())
                            .collect(Collectors.joining(", "));

                    highlights.add(
                            resolveText(isSpanish, InfoCvConstants.TECH_SPA, InfoCvConstants.TECH_ENG) + stringSkills);

                    var company = "";

                    if (e.getCompany() != null){
                        company = isSpanish ? e.getCompany(): e.getCompany().replace("Cliente", "Customer");
                    }

                    return Experience.builder()
                            .company(company)
                            .position(resolveText(isSpanish, e.getPosition(), e.getPositionEng()))
                            .startDate(e.getYearStart())
                            .endDate(e.getYearEnd())
                            .location(resolveText(isSpanish, e.getLocation(), e.getLocationEng()))
                            .summary(resolveText(isSpanish, e.getSummaryPdf(), e.getSummaryPdfEng()))
                            .highlights(highlights)
                            .build();
                })
                .toList();
    }

    private List<Skill> getSkills(boolean isSpanish) {
        return skillService.skillGet().stream()
                .map(s -> {
                    String details = s.getSkillSons().stream()
                            .map(son -> resolveText(isSpanish, son.getName(), son.getNameEng()))
                            .filter(name -> name != null && !name.isBlank())
                            .collect(Collectors.joining(", "));

                    return Skill.builder()
                            .label(resolveText(isSpanish, s.getName(), s.getNameEng()))
                            .details(details)
                            .build();
                })
                .toList();
    }

    private List<SelectedHonor> getSelectedHonors(boolean isSpanish, BasicDataResponse basicData) {
        var wrappers = isSpanish ? basicData.getWrapper() : basicData.getWrapperEng();
        return Optional.ofNullable(wrappers)
                .orElse(Collections.emptyList())
                .stream()
                .map(wrapper -> SelectedHonor.builder().bullet(wrapper).build())
                .toList();
    }

    private LinkedHashMap<String, Object> getSections(BasicDataResponse basicData, String language) {
        validateBasicDataForCv(basicData);
        boolean isSpanish = InfoCvConstants.LANGUAGE_SPANISH.equals(language);
        var educations = getEducations(isSpanish);
        var experiences = getExperiences(isSpanish);
        var skills = getSkills(isSpanish);
        var selectedHonors = getSelectedHonors(isSpanish, basicData);
        var sections = new LinkedHashMap<String, Object>();
        
        // Profile Section
        var profileLabel = isSpanish ? InfoCvConstants.PROFILE_RESUME_SPA : InfoCvConstants.PROFILE_RESUME_ENG;
        sections.put(profileLabel, isSpanish ? basicData.getDescriptionPdf() : basicData.getDescriptionPdfEng());

        // Other Sections
        sections.put(isSpanish ? InfoCvConstants.EDUCATION_SPA : InfoCvConstants.EDUCATION_ENG, educations);
        sections.put(isSpanish ? InfoCvConstants.EXPERIENCE_SPA : InfoCvConstants.EXPERIENCE_ENG, experiences);
        sections.put(isSpanish ? InfoCvConstants.SKILLS_SPA : InfoCvConstants.SKILLS_ENG, skills);

        if (!selectedHonors.isEmpty()) {
            sections.put(isSpanish ? InfoCvConstants.HONOR_SPA : InfoCvConstants.HONOR_ENG, selectedHonors);
        }

        return sections;
    }

    private String resolveText(boolean isSpanish, String spanishText, String englishText) {
        return isSpanish ? spanishText : englishText;
    }

    private void validateBasicDataForCv(BasicDataResponse basicData) {
        if (basicData.getDescription() == null || basicData.getDescriptionEng() == null) {
            throw new RenderCvServiceException("Basic Data descriptions cannot be null for CV generation", null);
        }
    }

    private HomeResponse getHomeResponse() {
        return homeRepository.findFirstByOrderByCreatedDesc()
                .map(entity -> homeMapper.toResponse(entity, s3Service))
                .orElse(null);
    }

    private BasicDataResponse getBasicDataResponse() {
        return basicDataRepository.findFirstByOrderByCreatedDesc()
                .map(basicDataMapper::toBasicDataResponse)
                .orElse(null);
    }
}
