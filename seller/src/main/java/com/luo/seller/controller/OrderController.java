package com.luo.seller.controller;

import com.luo.entity.Order;
import com.luo.entity.Product;
import com.luo.seller.params.OrderParam;
import com.luo.seller.service.OrderService;
import com.luo.seller.service.ProductRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private ProductRpcService productRpcService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public Product findOne(@PathVariable("id") String id) {
        return productRpcService.findOne(id);
    }

    @PostMapping("")
    public Order apply(@RequestHeader String authId, @RequestHeader String sign,
                       @RequestBody OrderParam param) {
        logger.info("apply");
        Order order = new Order();

        BeanUtils.copyProperties(param, order);
        order = orderService.apply(order);
        return order;
    }

}
