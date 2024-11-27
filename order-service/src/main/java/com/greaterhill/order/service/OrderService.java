package com.greaterhill.order.service;

import com.greaterhill.framework.model.CommonResponseObject;
import com.greaterhill.order.model.OrderRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    CommonResponseObject createOrder(OrderRequestDto request);

    Object getOrders(String orderNumber);
}
