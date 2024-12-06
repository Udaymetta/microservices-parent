package com.greaterhill.inventory.feign;

import com.greaterhill.framework.config.FeignConfig;
import com.greaterhill.framework.exception.InternalException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", configuration = FeignConfig.class)
public interface ProductInterface {

    @GetMapping("/api/product/{id}")
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackGetData")
    @Retry(name = "default")
    ResponseEntity<Object> getProducts(@PathVariable Integer id);

    default ResponseEntity<Object> fallbackGetData(Integer id, Throwable throwable) {
        System.out.println("Fallback response: product-service unavailable");
        throw new InternalException("Fallback response: product-service unavailable");
    }
}