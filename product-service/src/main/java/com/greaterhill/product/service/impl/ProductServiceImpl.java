package com.greaterhill.product.service.impl;

import com.greaterhill.framework.model.CommonResponseObject;
import com.greaterhill.product.dao.ItemDao;
import com.greaterhill.product.entity.Item;
import com.greaterhill.product.model.CreateProductDto;
import com.greaterhill.product.model.ProductResponseDto;
import com.greaterhill.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ItemDao itemDao;

    @Override
    public CommonResponseObject createProduct(CreateProductDto request) {
        Item item = new Item();
        item.setName(request.getItemName());
        item.setType(request.getItemType());
        itemDao.save(item);
        return CommonResponseObject.builder()
                .status("ok")
                .message("success")
                .build();
    }

    @Override
    public Object getAllProducts(Integer id) {
        if(id != null){
            Boolean isPresent = itemDao.findById(id).isPresent();
            return isPresent;
        }
        List<Item> itemList = itemDao.findAll();
        return itemList.stream().map(item ->
                new ProductResponseDto(item.getId(), item.getName(), item.getType())
        ).toList();
    }
}
