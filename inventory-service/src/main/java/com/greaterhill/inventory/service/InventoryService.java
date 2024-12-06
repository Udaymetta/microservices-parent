package com.greaterhill.inventory.service;

import com.greaterhill.framework.model.CommonResponseObject;
import com.greaterhill.inventory.model.InventoryRequestDto;
import com.greaterhill.inventory.model.InventoryStockResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    CommonResponseObject createOrUpdateInventory(InventoryRequestDto request);

    List<InventoryStockResponse> getStockAvailability(List<Integer> itemIdList);
}
