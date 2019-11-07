package com.luo.manager.controller;

import com.luo.entity.Product;
import com.luo.entity.enums.ProductStatus;
import com.luo.util.RestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
//随机端口
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private static RestTemplate rest = new RestTemplate();

    @Value("http://localhost:${local.server.port}/manager")
    private String baseUr;
    // 正常 数据
    private static List<Product> normals = new ArrayList<>();

    @BeforeClass
    public static void init() {
        Product p1 = new Product("T001", "灵活包1好", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(2.1));
        Product p2 = new Product("T002", "灵活包2好", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(2.33));
        Product p3 = new Product("T002", "灵活包3好", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(2), BigDecimal.valueOf(1.32));

        normals.add(p1);
        normals.add(p2);
        normals.add(p3);

    }

    @Test
    public void create() {
        normals.forEach(
                product -> {
                    Product p = RestUtil.postJson(rest, baseUr + "/products", product, Product.class);
                    Assert.notNull(p, "插入失败");
                }
        );
    }
}
