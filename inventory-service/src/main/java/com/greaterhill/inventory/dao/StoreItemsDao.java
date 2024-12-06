package com.greaterhill.inventory.dao;

import com.greaterhill.inventory.entity.StoreItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreItemsDao extends JpaRepository<StoreItems, Integer> {

    List<StoreItems> findByIdIn(List<Integer> itemId);
}
