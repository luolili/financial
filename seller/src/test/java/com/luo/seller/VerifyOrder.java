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

    //use 其他名字
   /* @Autowired
    @Qualifier("backupOrderRepository")//注入的名字 是backupOrderRepository
    private OrderRepository backOrderRepository;*/
    //use 其他名字
    @Autowired
    @Qualifier("readorderRepository")//注入的名字 是backupOrderRepository
    private OrderRepository readOrderRepository;//属性可以任意写

    @Autowired
    private OrderRepository orderRepository;

    // 读写 分离测试

    /**
     * JpaRepositoriesRegistrar--RepositoryBeanDefinitionRegistrarSupport
     * registerBeanDefinition
     * RepositoryConfigurationDelegate registerRepositoriesIn
     * beanName 断点
     * com.luo.seller.configuration.DataAccessConfiguration$BackupConfiguration
     *
     * 实现读写分离 的方法：
     * 1. 读的repo和写的repo放在不同的包
     * 2.写注解：拦截 beanName
     * 升级boot2 测试
     */
    @Test
    public void queryOrder() {
        //List<Order> all = backOrderRepository.findAll();
        List<Order> a2 = orderRepository.findAll();
        List<Order> a3 = readOrderRepository.findAll();

        //System.out.println("backup:" + all);
        System.out.println("pri:" + a2);
        System.out.println("read repo:" + a3);
    }
}
