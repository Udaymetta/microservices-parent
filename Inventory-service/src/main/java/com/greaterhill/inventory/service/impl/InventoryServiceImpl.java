package com.greaterhill.inventory.service.impl;

import com.greaterhill.exception.InternalException;
import com.greaterhill.inventory.dao.StoreItemsDao;
import com.greaterhill.inventory.entity.StoreItems;
import com.greaterhill.inventory.model.InventoryRequestDto;
import com.greaterhill.inventory.model.InventoryStockResponse;
import com.greaterhill.inventory.service.InventoryService;
import com.greaterhill.model.CommonResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InventoryServiceImpl implements InventoryService {

    private final StoreItemsDao storeItemsDao;
    private final RestTemplate restTemplate;
    @Value("${app.secretkey}")
    private final String secretKey;

    @Override
    public CommonResponseObject createOrUpdateInventory(InventoryRequestDto request) {
        StoreItems storeItems = new StoreItems();
        storeItems.setId(request.getItemId());
        storeItems.setQuantity(request.getQuantity());
        String url = "http://product-service/api/product/" + request.getItemId();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, secretKey);
        Boolean isItemExists = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class).getBody();
        if(Boolean.TRUE.equals(isItemExists)) {
            storeItemsDao.save(storeItems);
            return CommonResponseObject.builder()
                    .status("ok")
                    .message("success")
                    .build();
        }
        else {
            throw new InternalException("Item not present");
        }
    }

    @Override
    public List<InventoryStockResponse> getStockAvailability(List<Integer> itemIdList) {
        List<StoreItems> itemList;
        if(itemIdList != null && !itemIdList.isEmpty())
            itemList = storeItemsDao.findByIdIn(itemIdList);
        else
            itemList = storeItemsDao.findAll();
        return itemList.stream().map(item ->
                new InventoryStockResponse(item.getId(), item.getQuantity(),item.getQuantity() > 0)
        ).toList();
    }
}
