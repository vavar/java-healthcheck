package io.assignment.healthcheck.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource(value = "file:config.properties", ignoreResourceNotFound = true)
@Data
public class HealthCheckConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${healthcheck.report.url}")
    private String url;

    @Value("${healthcheck.report.token}")
    private String token;

}
