package com.luo.manager.configurations;

import com.luo.manager.controller.ProductController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
//@EnableSwagger2
public class SwaggerConfiguration01 {

    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("controller")
                .apiInfo(apiInfo())
                .select()
                //优化1：自定义 展示接口
                // .apis(RequestHandlerSelectors.basePackage("com.luo.manager"))
                //or
                .apis(RequestHandlerSelectors.basePackage(ProductController.class.getPackage().getName()))
                //or
                // .paths(PathSelectors.ant("/products/*"))
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("HTTP")
                .description("管理端接口")
                .termsOfServiceUrl("http:springfox.io")
                .license("license")
                // .licenseUrl("")
                .version("2.0")
                .build();
    }
}
