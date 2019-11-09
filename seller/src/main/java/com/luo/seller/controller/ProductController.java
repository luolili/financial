package com.luo.seller.controller;

import com.luo.entity.Product;
import com.luo.seller.service.ProductRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRpcService productRpcService;

    // 请求2次，第一次 有log,第二次没有
    @GetMapping("/{id}")
    public Product findOne(@PathVariable("id") String id) {
        return productRpcService.findOne(id);
    }
}
