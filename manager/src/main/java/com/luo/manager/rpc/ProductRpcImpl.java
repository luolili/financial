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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AutoJsonRpcServiceImpl
@Service
public class ProductRpcImpl implements ProductRpc {
    private static Logger logger = LoggerFactory.getLogger(ProductRpcImpl.class);
    @Autowired
    private ProductService productService;

    @Override
    public List<Product> query(ProductRpcReq req) {
        logger.info("查询");
        req.setPage(0);
        req.setPageSize(1000);
        req.setDirection(Sort.Direction.DESC);
        req.setOrderBy("rewardRate");
        Pageable pageable = new PageRequest(req.getPage(), req.getPageSize(), req.getDirection(), req.getOrderBy());
        Page<Product> page = productService.query(req.getIdList(), req.getMinRewardRate(),
                req.getMaxRewardRate(),
                req.getStatusList(), pageable);
        logger.info("res:{}", page);
        return page.getContent();
    }

    @Override
    public Product findOne(String id) {
        logger.info("ff");
        Product one = productService.findOne(id);
        logger.info("re:{}", one);
        return one;
    }
}
