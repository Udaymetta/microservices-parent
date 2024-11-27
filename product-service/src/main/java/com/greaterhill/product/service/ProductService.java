package com.greaterhill.product.service;

import com.greaterhill.framework.model.CommonResponseObject;
import com.greaterhill.product.model.CreateProductDto;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    CommonResponseObject createProduct(CreateProductDto request);

    Object getAllProducts(Integer id);
}
