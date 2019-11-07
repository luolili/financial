package com.luo.manager.controller;

import com.luo.entity.Product;
import com.luo.manager.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

    @PostMapping("")
    public Product addProduct(Product product) {
        Product result = productService.addProduct(product);

        return result;
    }

    // 手动 postman:冒烟测试，所以需要自动化测试
    @GetMapping(value = "/{id}")
    public Product get(@PathVariable("id") String id) {
        logger.debug("根据id查询");
        Product result = productService.findOne(id);
        logger.debug("current product:{}", result);
        return result;
    }

    @GetMapping(value = "")
    public Page<Product> getAll(String ids, BigDecimal minRewardRate, BigDecimal maxRewardRate, String status,
                                @RequestParam(defaultValue = "0") int pageNum,
                                @RequestParam(defaultValue = "10") int pageSize) {
        logger.debug("多条件查询");
        List<String> idList = null, statusList = null;
        if (!StringUtils.isEmpty(ids)) {
            idList = Arrays.asList(ids.split(","));
        }

        if (!StringUtils.isEmpty(status)) {
            statusList = Arrays.asList(status.split(","));
        }

        Pageable pageable = new PageRequest(pageNum, pageSize);
        Page<Product> page = productService.query(idList, minRewardRate, maxRewardRate, statusList, pageable);
        return page;
    }

}
