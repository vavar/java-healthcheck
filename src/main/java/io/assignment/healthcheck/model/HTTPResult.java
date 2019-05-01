package io.assignment.healthcheck.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public
class HTTPResult {
    private boolean isSuccess;
    private long time;
}
