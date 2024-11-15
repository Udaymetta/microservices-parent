package com.greaterhill.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "store_items")
@Data
public class StoreItems {

    @Id
    private Integer id;
    private Integer quantity;
}
