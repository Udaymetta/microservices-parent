package com.greaterhill.inventory.model;

import lombok.Data;

@Data
public class InventoryRequestDto {

    private Integer itemId;
    private Integer quantity;
}
