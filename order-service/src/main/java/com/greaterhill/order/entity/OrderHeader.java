package com.greaterhill.order.entity;

import com.greaterhill.order.model.OrderLineItemsDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_header")
@Data
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderHeaderId;
    private String orderNumber;
    private String customerName;
    private String phoneNumber;
    private Date orderDate;
    private double subTotal;
    @OneToMany(mappedBy = "orderHeader", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}