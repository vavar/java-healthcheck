package io.assignment.healthcheck.service;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

public class CSVFileServiceTest {

    private CSVFileService fixture;

    @Before
    public void setup() {
        fixture = new CSVFileService();
    }

    @Test
    public void givenCSVFileInTestResouce_WhenParseIsCall_ThenReturnURLList() {
        URL fileNameURL = CSVFileServiceTest.class.getResource("/test.csv");
        String[] urls = fixture.parse(fileNameURL.getPath());
        assertArrayEquals(new String[]{ "https://www.google.com", "https://www.facebook.com"}, urls);
    }
}