package com.greaterhill.order.dao;

import com.greaterhill.order.entity.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHeaderDao extends JpaRepository<OrderHeader, Integer> {

    OrderHeader findByOrderNumber(String orderNumber);
}
