package com.luo.seller.service;

import com.luo.api.ProductRpc;
import com.luo.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 常见err:
 * 1.先要启动manager,在启动seller,否则 数据库连接失败
 * 2. RpcConfiguration 必须制定要扫描的 rpc接口：NPE
 * 3.rpc 地址：yml 地址 必须以 / 结束；管理端 的 rpc 接口不以 / 开始
 * 4. json 格式化错误：rpc 的接口的参数不能是接口如：Pageable和复杂对象
 */
@Service
public class ProductRpcService implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = LoggerFactory.getLogger(ProductRpcService.class);
    @Autowired
    private ProductRpc productRpc;

    @Autowired
    private ProductCache productCache;

    //初始化缓存
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Product> productList = findAll();
        productList.forEach(p -> {
            productCache.putCache(p);
        });
    }

    public List<Product> findAll() {
       /* ProductRpcReq req = new ProductRpcReq();
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
        return result;*/
        return productCache.readAllCache();

    }

    //test findAll
    //@PostConstruct
    public void testFindAll() {
        findAll();
    }

    //@Cacheable(cacheNames = {"my_product"})
    public Product findOne(String id) {
        /*logger.info("单个 产品 id 查询：{}", id);
        Product one = productRpc.findOne(id);
        logger.info("res p:{}", one);*/
        Product product = productCache.readCache(id);
        //防止cache 里面的null 数据
        if (null == product) {
            productCache.readCache(id);
        }
        return product;
    }

    /**
     * log:
     * c.g.j.s.AutoJsonRpcClientProxyCreator    : |Scanning 'classpath:com/luo/api/星星/:' for JSON-RPC service interfaces.
     * DEBUG 7012 --- [           main] c.g.j.s.AutoJsonRpcClientProxyCreator
     * : Found JSON-RPC service to proxy [com.luo.api.ProductRpc] on path 'rpc/products'.
     */
    //@PostConstruct
    public void init() {
        findOne("T001");
    }
}
