package com.greaterhill.product.service.impl;

import com.greaterhill.model.CommonResponseObject;
import com.greaterhill.product.dao.ItemDao;
import com.greaterhill.product.entity.Item;
import com.greaterhill.product.model.CreateProductDto;
import com.greaterhill.product.model.ProductResponseDto;
import com.greaterhill.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
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
            return itemDao.findById(id).isPresent();
        }
        List<Item> itemList = itemDao.findAll();
        return itemList.stream().map(item ->
                new ProductResponseDto(item.getId(), item.getName(), item.getType())
        ).toList();
    }
}
