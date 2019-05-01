package io.assignment.healthcheck.service;

import io.assignment.healthcheck.config.HealthCheckConfig;
import io.assignment.healthcheck.model.HealthCheckReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HealthCheckConfig config;

    @Captor
    private ArgumentCaptor<HttpEntity<HealthCheckReport>> captor;

    @InjectMocks
    private ReportService fixture = new ReportService();

    @Test
    public void givenMockRestTemplate_WhenCallReportAPI_ThenExpectedRequestShouldBeMatched() {

        String url = "http://www.google.com";
        String token = "aaa";

        Mockito.when(config.getUrl()).thenReturn(url);
        Mockito.when(config.getToken()).thenReturn(token);
        Mockito.when(restTemplate.postForEntity(eq(url), captor.capture(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("", HttpStatus.OK));

        HealthCheckReport report = new HealthCheckReport(1,1,0, 10);
        fixture.send(report);

        HttpEntity<HealthCheckReport> actual = captor.getValue();
        assertEquals(report, actual.getBody());
        assertEquals("Bearer "+token, actual.getHeaders().get("Authorization").get(0));
        assertEquals("application/json", actual.getHeaders().get("Content-Type").get(0));
    }
}