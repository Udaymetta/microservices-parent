package com.greaterhill.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponseDto {

    private String orderNumber;
    private String customerName;
    private String phoneNumber;
    private Date orderDate;
    private double subTotal;
    private List<OrderLineItemsDto> lineItems;
}
