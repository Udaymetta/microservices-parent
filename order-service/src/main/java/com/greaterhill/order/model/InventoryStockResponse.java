package com.greaterhill.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryStockResponse {

    private Integer id;
    private Integer quantity;
    private Boolean inStock;
}
