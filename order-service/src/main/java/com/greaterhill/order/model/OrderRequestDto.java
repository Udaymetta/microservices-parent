package com.greaterhill.order.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    private String customerName;
    private String phoneNumber;
    private double subTotal;
    private List<OrderLineItemsDto> lineItems;
}
