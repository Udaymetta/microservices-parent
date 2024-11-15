package com.greaterhill.product.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String type;
}
