package io.assignment.healthcheck.service;

import io.assignment.healthcheck.config.HealthCheckConfig;
import io.assignment.healthcheck.model.HealthCheckReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HealthCheckConfig healthCheckConfig;

    public void send(HealthCheckReport report) {
        String token = healthCheckConfig.getToken();
        String url = healthCheckConfig.getUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + token);
        headers.set("Content-Type", "application/json");

        HttpEntity<HealthCheckReport> request = new HttpEntity<>(report, headers);
        try {
            restTemplate.postForEntity(url, request, String.class);
        }catch(Exception ex) {
            log.warn("send report failed", ex);
        }
    }
}
