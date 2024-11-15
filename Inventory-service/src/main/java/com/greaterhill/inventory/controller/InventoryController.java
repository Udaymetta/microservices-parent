package com.greaterhill.inventory.controller;

import com.greaterhill.inventory.model.InventoryRequestDto;
import com.greaterhill.inventory.model.InventoryStockResponse;
import com.greaterhill.inventory.service.InventoryService;
import com.greaterhill.model.CommonResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<CommonResponseObject> createInventory(@RequestBody InventoryRequestDto request){
        return ResponseEntity.ok(inventoryService.createOrUpdateInventory(request));
    }

    @GetMapping
    public ResponseEntity<List<InventoryStockResponse>> getIsInStock(
            @RequestParam(required = false) List<Integer> itemIdList){
        return ResponseEntity.ok(inventoryService.getStockAvailability(itemIdList));
    }
}
