package io.assignment.healthcheck.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthCheckReport{
    @JsonProperty("total_websites")
    private int totalWebsites;
    private int success;
    private int failure;
    @JsonProperty("total_time")
    private long totalTime;


    public static Builder builder() {
        return new HealthCheckReport.Builder();
    }

    public static class Builder {
        private int totalWebsites;
        private int success;
        private int failure;
        private long totalTime;

        public Builder add(HTTPResult result) {
            if (result.isSuccess()) {
                this.success++;
            }else {
                this.failure++;
            }
            totalWebsites++;
            totalTime += result.getTime();
            return this;
        }

        public Builder combine(Builder report) {
            success += report.success;
            failure += report.failure;
            totalWebsites += report.totalWebsites;
            totalTime += report.totalTime;
            return this;
        }

        public HealthCheckReport build() {
            return new HealthCheckReport(totalWebsites, success, failure, totalTime);
        }
    }
}
