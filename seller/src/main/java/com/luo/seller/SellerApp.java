package com.luo.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * rpc 客户端
 * 通过日志 看 运行原理
 */

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableCaching
@EnableJpaRepositories("com.luo.seller.repositories")
@EntityScan("com.luo.entity")
@EnableScheduling
public class SellerApp {
    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class, args);
    }
}
