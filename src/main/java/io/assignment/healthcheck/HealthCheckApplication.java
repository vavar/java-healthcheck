package io.assignment.healthcheck;

import io.assignment.healthcheck.model.HealthCheckReport;
import io.assignment.healthcheck.service.CSVFileService;
import io.assignment.healthcheck.service.HealthCheckService;
import io.assignment.healthcheck.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAsync
public class HealthCheckApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(HealthCheckApplication.class);

	@Autowired
	private CSVFileService csvFileService;

	@Autowired
	private HealthCheckService healthcheckService;

	@Autowired
	private ReportService reportService;

	public static void main(String[] args) {
		SpringApplication.run(HealthCheckApplication.class, args);
	}

	@Override
	public void run(String... args) {

		long start = System.nanoTime();

		String[] urls = args.length > 0 ? csvFileService.parse(args[0]) : new String[]{};
		if (urls.length == 0) {
			return;
		}

		System.out.println("Perform website checking...");
		HealthCheckReport report = healthcheckService.check(urls);
		reportService.send(report);
		System.out.println("Done!");

		//Print operation result to console
		System.out.println("\nChecked websites: " + report.getTotalWebsites());
		System.out.println("Successful websites: " + report.getSuccess());
		System.out.println("Failure websites: " + report.getFailure());
		System.out.println("Total times to finished checking website: "
				+ TimeUnit.MILLISECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS));

	}

}
