package com.luo.manager.service;

import com.luo.entity.Product;
import com.luo.entity.enums.OrderStatus;
import com.luo.entity.enums.OrderType;
import com.luo.entity.enums.ProductStatus;
import com.luo.manager.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class ProductService {
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {

        //数据校验
        checkProduct(product);
        setDefault(product);
        return null;
    }

    private void setDefault(Product product) {

        if (product.getCreateAt() == null) {
            product.setCreateAt(new Date());
        }
         if (product.getUpdateAt() == null) {
            product.setUpdateAt(new Date());
        }
        if (product.getStepAmount() == null) {
            product.setStepAmount(BigDecimal.ZERO);
        }
        if (product.getLockTerm() == null) {
            product.setLockTerm(0);
        }
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.AUDITING.getDesc());
        }
    }

    private void checkProduct(Product product) {

        Assert.notNull(product.getId(), "产品编号不能为空");
        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate())<0,
                "收益率范围错误");
    }
}
