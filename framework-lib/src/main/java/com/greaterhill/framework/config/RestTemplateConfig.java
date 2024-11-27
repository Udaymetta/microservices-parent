package com.greaterhill.framework.config;

import com.greaterhill.framework.exception.InternalException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Configuration
public class RestTemplateConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        // Configure the circuit breaker settings
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        return CircuitBreakerRegistry.of(circuitBreakerConfig);
    }

    @Bean
    public Retry retry() {
        return Retry.of("retryService", RetryConfig.custom()
                .maxAttempts(3) // Retry 3 times
                .waitDuration(Duration.ofMillis(500)) // Wait 500ms between retries
                .build());
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(CircuitBreakerRegistry circuitBreakerRegistry, Retry retry) {
        RestTemplate restTemplate = new RestTemplate();

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("restTemplateCircuitBreaker");
        // Add a ClientHttpRequestInterceptor to apply the circuit breaker
        ClientHttpRequestInterceptor interceptor = (request, body, execution) ->
                retry.executeSupplier(() ->
                        (ClientHttpResponse) circuitBreaker.executeSupplier(() -> {
                            try {
                                log.info("Circuit breaker event - {}", circuitBreaker.getState().toString());
                                return execution.execute(request, body);
                            } catch (IOException e) {
                                log.error("Request failed: {}", e.getMessage());
                                return fallback(request, body);
                            }
                        })
                );

        restTemplate.getInterceptors().add(interceptor);
        return restTemplate;
    }

    private ResponseEntity<String> fallback(HttpRequest request, byte[] body) {
        log.warn("Fallback invoked for request: {}", request.getURI());
        throw new InternalException("Service temporarily unavailable, please try again later.");
    }
}