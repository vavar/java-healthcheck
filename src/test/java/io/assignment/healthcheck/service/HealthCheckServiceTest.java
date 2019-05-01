package io.assignment.healthcheck.service;

import io.assignment.healthcheck.model.HealthCheckReport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HealthCheckServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HealthCheckService fixture = new HealthCheckService();

    @Test
    public void givenMockRestTemplate_WhenCheckSingleValidURL_ThenReturnSuccessResult() {
        HealthCheckReport expectedReport = new HealthCheckReport(1,1,0,0);
        Mockito.when(restTemplate.getForEntity("http://www.google.com", String.class))
          .thenReturn(new ResponseEntity<>("", HttpStatus.OK));

        HealthCheckReport actualReport = fixture.check(new String[]{"http://www.google.com"});
        assertEquals(expectedReport.getSuccess(), actualReport.getSuccess());
        assertEquals(expectedReport.getFailure(), actualReport.getFailure());
        assertEquals(expectedReport.getTotalWebsites(), actualReport.getTotalWebsites());
        assertTrue( actualReport.getTotalTime() > 0 );
    }

    @Test
    public void givenMockRestTemplate_WhenCheckSingleInvalidURL_ThenReturnFailureResult() {
        HealthCheckReport expectedReport = new HealthCheckReport(1,0,1,0);
        Mockito.when(restTemplate.getForEntity("http://www.google.com", String.class))
                .thenReturn(new ResponseEntity<>("", HttpStatus.NOT_FOUND));

        HealthCheckReport actualReport = fixture.check(new String[]{"http://www.google.com"});
        assertEquals(expectedReport.getSuccess(), actualReport.getSuccess());
        assertEquals(expectedReport.getFailure(), actualReport.getFailure());
        assertEquals(expectedReport.getTotalWebsites(), actualReport.getTotalWebsites());
        assertTrue( actualReport.getTotalTime() > 0 );
    }

    @Test
    public void givenMockRestTemplate_WhenCheckBothValidAndInvalidURL_ThenReturnSuccessAndFailureResult() {
        HealthCheckReport expectedReport = new HealthCheckReport(2,1,1,0);
        Mockito.when(restTemplate.getForEntity("http://www.google.com", String.class))
                .thenReturn(new ResponseEntity<>("", HttpStatus.NOT_FOUND));
        Mockito.when(restTemplate.getForEntity("http://www.facebook.com", String.class))
                .thenReturn(new ResponseEntity<>("", HttpStatus.OK));

        HealthCheckReport actualReport = fixture.check(new String[]{"http://www.google.com","http://www.facebook.com"});
        assertEquals(expectedReport.getSuccess(), actualReport.getSuccess());
        assertEquals(expectedReport.getFailure(), actualReport.getFailure());
        assertEquals(expectedReport.getTotalWebsites(), actualReport.getTotalWebsites());
        assertTrue( actualReport.getTotalTime() > 0 );
    }
}