package com.greaterhill.product.controller;

import com.greaterhill.framework.model.CommonResponseObject;
import com.greaterhill.product.model.CreateProductDto;
import com.greaterhill.product.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponseObject> createProduct(@RequestBody CreateProductDto request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping({"", "/{id}"})
    public ResponseEntity<Object> getProducts(
            @PathVariable(required = false) Integer id
    ){
        return ResponseEntity.ok(productService.getAllProducts(id));
    }
}
