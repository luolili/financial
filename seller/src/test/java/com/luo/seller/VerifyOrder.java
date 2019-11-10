package com.luo.seller;

import com.luo.entity.Order;
import com.luo.seller.repositories.OrderRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
//随机端口
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//指定方法执行顺序，按照 方法名字的字典顺序
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VerifyOrder {

    //默认 注入的名字是backupOrderRepository，否则报错
    @Autowired
    private OrderRepository backupOrderRepository;

    //是哟个其他名字
    @Autowired
    @Qualifier("backupOrderRepository")//注入的名字 是backupOrderRepository
    private OrderRepository backOrderRepository;

    @Autowired
    private OrderRepository orderRepository;

    // 读写 分离测试
    @Test
    public void queryOrder() {
        List<Order> all = backOrderRepository.findAll();
        List<Order> a2 = orderRepository.findAll();

        System.out.println("backup:" + all);
        System.out.println("pri:" + a2);
    }
}
