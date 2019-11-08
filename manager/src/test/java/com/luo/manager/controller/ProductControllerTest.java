package com.luo.manager.controller;

import com.luo.entity.Product;
import com.luo.entity.enums.ProductStatus;
import com.luo.util.RestUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Gradle --Task--build--build :自动化测试
 * 失败的话，jar/war不成功
 * 直接运行测试类：查看多个测试方法的顺序
 */
@RunWith(SpringRunner.class)
//随机端口
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//指定方法执行顺序，按照 方法名字的字典顺序
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
        Product p3 = new Product("T003", "灵活包3好", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(2), BigDecimal.valueOf(1.32));

        normals.add(p1);
        normals.add(p2);
        normals.add(p3);

    }

    @Test
    public void create() {
        normals.forEach(
                product -> {
                    Product p = RestUtil.postJSON(rest, baseUr + "/products", product, Product.class);
                    Assert.notNull(p, "插入失败");
                }
        );

    }

    @Test
    public void findOne() {
        normals.forEach(
                product -> {
                    Product p = rest.getForObject(baseUr + "/products/" + product.getId(), Product.class);
                    Assert.isTrue(p.getId().equals(product.getId()), "查询失败");
                }
        );

    }

    @Test
    public void create01() {
        Product p1 = new Product("T001", "灵活包1好", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(2.1));
        Product p = RestUtil.postJSON(rest, baseUr + "/products", p1, Product.class);
    }


}
