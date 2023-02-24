package com.hcano.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSlowCallRateExceededEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSuccessEvent;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CircuitBreakerListenerConfig {
    @Bean
    public RegistryEventConsumer<CircuitBreaker> myRegistryEventConsumer() {

        return new RegistryEventConsumer<CircuitBreaker>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher()
                        .onSuccess(this::successEvent)
                        .onError(this::errorEvent)
                        .onStateTransition(this::changeTransitionStatusEvent)
                        .onSlowCallRateExceeded(this::slowCallRateExceededEvent);
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {

            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {

            }

            private void successEvent(CircuitBreakerOnSuccessEvent event) {
                log.info("Circuit Breaker Event {}", event.getEventType());
            }

            private void errorEvent(CircuitBreakerOnErrorEvent event) {
                log.error("Circuit Breaker Event {}, with error {}", event.getEventType(), event.getThrowable().getMessage());
            }

            private void changeTransitionStatusEvent(CircuitBreakerOnStateTransitionEvent event) {
                log.info("Circuit Breaker Event {}, status changed from {} to {}", event.getEventType(), event.getStateTransition().getFromState(), event.getStateTransition().getToState());
            }

            private void slowCallRateExceededEvent(CircuitBreakerOnSlowCallRateExceededEvent event) {
                log.error("Circuit Breaker Event {}, slow rate {}%", event.getEventType(), event.getSlowCallRate());
            }
        };
    }
}
