package com.cristiansrc.resume.msresume.infrastructure.client.rendercv;

import com.cristiansrc.resume.msresume.application.exception.RenderCvServiceException;
import com.cristiansrc.resume.msresume.application.port.output.client.IRenderCvClient;
import com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in.CustomerCvIn;
import com.cristiansrc.resume.msresume.infrastructure.client.rendercv.out.CustomerCvOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RenderCvClient implements IRenderCvClient {

    private final RestTemplate restTemplate;

    @Value("${config.rendercv.url}")
    private String renderCvUrl;

    @Override
    public CustomerCvOut renderCv(CustomerCvIn customerCvIn) {
        log.info("Calling RenderCV service at {}", renderCvUrl);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CustomerCvIn> request = new HttpEntity<>(customerCvIn, headers);

        try {
            String url = renderCvUrl + "/render";
            return restTemplate.postForObject(url, request, CustomerCvOut.class);
        } catch (Exception e) {
            log.error("Error calling RenderCV service", e);
            throw new RenderCvServiceException("Error generating PDF via RenderCV service", e);
        }
    }
}
