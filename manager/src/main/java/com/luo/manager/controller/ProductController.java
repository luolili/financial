package com.luo.manager.controller;

import com.luo.entity.Product;
import com.luo.manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("")
    public Product addProduct(Product product) {
        Product result = productService.addProduct(product);

        return result;
    }
}
