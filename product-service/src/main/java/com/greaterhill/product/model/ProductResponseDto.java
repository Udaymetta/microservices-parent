package com.greaterhill.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDto {

    private int id;
    private String itemName;
    private String itemType;
}
