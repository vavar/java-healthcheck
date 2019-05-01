package io.assignment.healthcheck.service;

import io.assignment.healthcheck.model.HTTPResult;
import io.assignment.healthcheck.model.HealthCheckReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;

@Service
public class HealthCheckService {

    @Autowired
    private RestTemplate restTemplate;

    public HealthCheckReport check(String[] urls) {
        return Arrays.stream(urls)
                .map(url -> CompletableFuture.supplyAsync(() -> fetch(url)))
                .map(CompletableFuture::join)
                .collect(Collector.of(HealthCheckReport::builder,
                        HealthCheckReport.Builder::add,
                        HealthCheckReport.Builder::combine,
                        HealthCheckReport.Builder::build));
    }

    private HTTPResult fetch(String url) {
        long start = System.nanoTime();
        try {
            ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
            HttpStatus statusCode = resp.getStatusCode();
            return new HTTPResult(statusCode == HttpStatus.OK, System.nanoTime() - start);
        }catch (Exception ex) {
            return new HTTPResult(false, System.nanoTime() - start);
        }
    }
}
