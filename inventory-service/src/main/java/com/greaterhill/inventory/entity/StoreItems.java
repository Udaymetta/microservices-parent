package com.greaterhill.inventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "store_items")
@Data
public class StoreItems {

    @Id
    private Integer id;
    private Integer quantity;
}
