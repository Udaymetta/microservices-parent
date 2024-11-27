package com.greaterhill.order.feign;

import com.greaterhill.framework.config.FeignConfig;
import com.greaterhill.framework.exception.InternalException;
import com.greaterhill.order.model.InventoryStockResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service/api/inventory", configuration = FeignConfig.class)

public interface InventoryInterface {

    @GetMapping
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackGetData")
    @Retry(name = "default")
    ResponseEntity<List<InventoryStockResponse>> getIsInStock(@RequestParam(required = false) List<Integer> itemIdList);

    default ResponseEntity<Object> fallbackGetData(List<Integer> itemIdList, Throwable throwable) {
        throw new InternalException("Fallback response: inventory-service unavailable");
    }
}