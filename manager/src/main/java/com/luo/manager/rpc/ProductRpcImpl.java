package com.luo.manager.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.luo.api.ProductRpc;
import com.luo.api.domain.ProductRpcReq;
import com.luo.entity.Product;
import com.luo.manager.controller.ProductController;
import com.luo.manager.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@AutoJsonRpcServiceImpl
@Service
public class ProductRpcImpl implements ProductRpc {
    private static Logger logger = LoggerFactory.getLogger(ProductRpcImpl.class);
    @Autowired
    private ProductService productService;

    @Override
    public Page<Product> query(ProductRpcReq req) {
        logger.info("查询");
        Page<Product> page = productService.query(req.getIdList(), req.getMinRewardRate(),
                req.getMaxRewardRate(),
                req.getStatusList(), req.getPageable());
        logger.info("res:{}", page);
        return page;
    }

    @Override
    public Product fidOe(String id) {
        logger.info("ff");
        Product one = productService.findOne(id);
        logger.info("re:{}", one);
        return one;
    }
}
