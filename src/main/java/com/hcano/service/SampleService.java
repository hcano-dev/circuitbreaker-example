package com.hcano.service;

import com.hcano.mock.ApiMock;
import com.hcano.constants.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleService {
    public static final String FALLBACK = "fallBackRunApiPostCall";

    private final ApiMock apiMock;

    public SampleService(ApiMock apiMock) {
        this.apiMock = apiMock;
    }

    @CircuitBreaker(name = Constants.CINET_SERVER_NAME, fallbackMethod = FALLBACK)
    public Integer runPostApiCall() {
        return this.apiMock.mockPostSlow(5);
    }

    private Integer fallBackRunApiPostCall(Throwable error) {
        log.info("Fallback is called, with error {}", error.getMessage());

        return 1;
    }
}
