package com.greaterhill.order.controller;

import com.greaterhill.framework.model.CommonResponseObject;
import com.greaterhill.order.model.OrderRequestDto;
import com.greaterhill.order.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CommonResponseObject> createOrder(@RequestBody OrderRequestDto request){
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping({"", "/{orderNumber}"})
    public ResponseEntity<Object> getOrders(
            @PathVariable (required = false) String orderNumber){
        return ResponseEntity.ok(orderService.getOrders(orderNumber));
    }
}
