package com.luo.seller.configuration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.luo.api.ProductRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@ComponentScan(basePackageClasses = {ProductRpc.class})
public class RpcConfiguration {
    private static Logger logger = LoggerFactory.getLogger(RpcConfiguration.class);

    @Bean
    public AutoJsonRpcClientProxyCreator autoJsonRpcClientProxyCreator(
            @Value("${rpc.manager.url}") String url) {
        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
        try {
            creator.setBaseUrl(new URL(url));
        } catch (MalformedURLException e) {
            logger.error("创建url err", e);
        }
        creator.setScanPackage(ProductRpc.class.getPackage().getName());
        return creator;
    }
}
