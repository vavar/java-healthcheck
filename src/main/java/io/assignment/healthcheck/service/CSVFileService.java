package io.assignment.healthcheck.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CSVFileService {
    private final Logger log = LoggerFactory.getLogger(CSVFileService.class);

    private static final String COMMA_DELIMITER = ",";

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public String[] parse(String fileName){
        List<String> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                records.addAll(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            log.error("read file failed", e);
        }
        return records.toArray(new String[0]);
    }
}
