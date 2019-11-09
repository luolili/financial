package com.luo.seller.service;

import com.hazelcast.core.HazelcastInstance;
import com.luo.api.ProductRpc;
import com.luo.api.domain.ProductRpcReq;
import com.luo.entity.Product;
import com.luo.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductCache {
    private static Logger logger = LoggerFactory.getLogger(ProductCache.class);
    private static final String CACHE_NAME = "my_product";
    @Autowired
    private ProductRpc productRpc;
    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Cacheable(cacheNames = CACHE_NAME)
    public Product readCache(String id) {
        logger.info("单个 产品 id 查询：{}", id);
        Product one = productRpc.findOne(id);
        logger.info("res p:{}", one);
        return one;
    }

    //修改 cache，key策略：没有参数：key=0;有一个参数key=param;多个参数：全部参数的hashcode
    @CachePut(cacheNames = CACHE_NAME, key = "#product.id")
    public Product putCache(Product product) {
        return product;
    }

    @CacheEvict(cacheNames = CACHE_NAME)
    public void removeCache(String id) {
    }

    //需要借助 haelcast
    public List<Product> readAllCache() {

        Map map = hazelcastInstance.getMap(CACHE_NAME);
        List<Product> productList = null;
        if (map.size() > 0) {
            productList = new ArrayList<>();
            productList.addAll(map.values());

        } else {
            productList = findAll();
        }
        return productList;
    }

    public List<Product> findAll() {
        ProductRpcReq req = new ProductRpcReq();
        List<String> status = new ArrayList<>();
        status.add(ProductStatus.IN_SELL.getDesc());
        Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC, "rewardRate");

        req.setStatusList(status);
        req.setPage(0);
        req.setPageSize(1000);
        req.setDirection(Sort.Direction.DESC);
        req.setOrderBy("rewardRate");
        logger.info("query all products: req:{}", req);
        List<Product> result = productRpc.query(req);
        logger.info("rpc res:{}", result);
        return result;

    }

}
