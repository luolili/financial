package com.luo.swagger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.luo.swagger"})
public class SwaggerConfiguration {
    @Autowired
    private SwaggerInfo swaggerInfo;

    @Bean
    public Docket controllerApi(SwaggerInfo swaggerInfo) {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerInfo.getGroupName())
                .apiInfo(apiInfo(swaggerInfo));

        ApiSelectorBuilder selectorBuilder = docket.select();
        if (StringUtils.isNotEmpty(swaggerInfo.getBasePackage())) {
            selectorBuilder.apis(RequestHandlerSelectors.basePackage(swaggerInfo.getBasePackage()));
        }
        if (StringUtils.isNotEmpty(swaggerInfo.getAntPath())) {
            selectorBuilder.paths(PathSelectors.ant(swaggerInfo.getAntPath()));
        }

        return selectorBuilder.build();
        /*return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerInfo.getGroupName())
                .apiInfo(apiInfo(swaggerInfo))
                .select()
                //优化1：自定义 展示接口
               // .apis(RequestHandlerSelectors.basePackage("com.luo.manager"))
                //or
                .apis(RequestHandlerSelectors.basePackage(ProductController.class.getPackage().getName()))
                //or
               // .paths(PathSelectors.ant("/products/*"))
                .build();*/
    }

    public ApiInfo apiInfo(SwaggerInfo swaggerInfo) {
        return new ApiInfoBuilder()
                .title(swaggerInfo.getTitle())
                .description(swaggerInfo.getDescription())
                .termsOfServiceUrl("http:springfox.io")
                .license(swaggerInfo.getLicense())
                // .licenseUrl("")
                .version("2.0")
                .build();
    }
}
