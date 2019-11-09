package com.luo.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}) rpc 服务端
@SpringBootApplication
@EnableJpaRepositories("com.luo.manager.repositories")
@EntityScan("com.luo.entity")
//方法1
//@Import({SwaggerConfiguration.class})
//方法2
//@EnableMySwagger
//方法3：spring.factories
public class ManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class, args);
    }
}
