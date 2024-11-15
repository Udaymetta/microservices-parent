package com.greaterhill.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderLineItemsDto {

    private Integer id;
    private Integer quantity;
    private double price;
}
