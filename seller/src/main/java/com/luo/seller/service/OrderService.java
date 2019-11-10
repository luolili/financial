package com.luo.seller.service;

import com.luo.entity.Order;
import com.luo.entity.Product;
import com.luo.entity.enums.OrderStatus;
import com.luo.entity.enums.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderService {
//    @Autowired
//    private OrderRepository orderRepository;

    @Autowired
    private ProductRpcService productRpcService;

    public Order apply(Order order) {

        checkOrder(order);
        completeOrder(order);
        //order = orderRepository.saveAndFlush(order);
        return order;
    }

    private void completeOrder(Order order) {

        order.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setOrderType(OrderType.APPLY.name());
        order.setOrderStatus(OrderStatus.SUCCESS.getDesc());
        order.setUpdateAt(new Date());
    }

    private void checkOrder(Order order) {

        Assert.notNull(order.getOuterOrderId(), "需要外部订单号");
        Assert.notNull(order.getChanId(), "需要渠道id");
        Assert.notNull(order.getChanUserId(), "需要渠道用户id");
        Assert.notNull(order.getProductId(), "需要产品id");
        Assert.notNull(order.getAmount(), "需要金钱");
        Assert.notNull(order.getCreateAt(), "需要 创建时间");
        //查询产品
        Product product = productRpcService.findOne(order.getProductId());
        Assert.notNull(product, "产品不存在");
        Assert.isTrue(order.getAmount().compareTo(product.getThresholdAmount()
        ) > 0, "钱不对");
    }
}
