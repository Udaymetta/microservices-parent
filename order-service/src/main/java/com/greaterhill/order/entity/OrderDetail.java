package com.greaterhill.order.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_detail")
@Data
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;
    private Integer itemId;
    private Integer quantity;
    private double price;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderHeaderId", nullable = false)
    private OrderHeader orderHeader;
}