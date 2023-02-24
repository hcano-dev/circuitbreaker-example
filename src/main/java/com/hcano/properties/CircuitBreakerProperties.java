package com.hcano.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "circuit-breaker", ignoreUnknownFields = false)
@PropertySource(value = "classpath:META-INF/build-info.properties", ignoreResourceNotFound = true)
@Getter
@Setter
public class CircuitBreakerProperties {
    private String slidingWindowType;
    private Integer slidingWindowSize;
    private Float failureRateThreshHold;
    private Integer permittedNumberOfCallsInHalfOpenState;
    private Boolean writableStackTraceEnabled;
    private Float slowCallRateThreshold;
    private Integer slowCallDurationThreshold;
    private Boolean automaticTransitionFromOpenToHalfOpenEnabled;

    @Getter
    private WaitIntervalFunctionInOpenState waitIntervalFunctionInOpenState = new WaitIntervalFunctionInOpenState();

    @Getter
    @Setter
    public static class WaitIntervalFunctionInOpenState {
        private Integer startSeconds;
        private Integer exponent;
        private Integer maxSeconds;
    }
}

