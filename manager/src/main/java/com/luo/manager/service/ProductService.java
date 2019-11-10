package com.luo.manager.service;

import com.luo.entity.Product;
import com.luo.entity.enums.ProductStatus;
import com.luo.manager.error.ErrorEnum;
import com.luo.manager.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        logger.info("创建产品");
        //数据校验
        checkProduct(product);
        setDefault(product);
        return productRepository.save(product);
    }

    public Product findOne(String id) {
        logger.info("根据id查询");
        Assert.notNull(id,"id 不能为空");
        //Product one = productRepository.findOne(id);
        //boot2
        Product product = productRepository.findById(id).orElse(null);

        return product;
    }

    //付咋查询
    public Page<Product> query(List<String> idList, BigDecimal minRate, BigDecimal maxRate,
                               List<String> statusList, Pageable pageable) {
        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Expression<String> idCol = root.get("id");
                Expression<String> statusCol = root.get("status");
                Expression<BigDecimal> rewardRateCol = root.get("rewardRate");
                List<Predicate> predicateList = new ArrayList<>();
                if (idList != null && idList.size() > 0) {
                    predicateList.add(idCol.in(idList));
                }
                if (minRate != null && BigDecimal.ZERO.compareTo(minRate) < 0) {
                    predicateList.add(cb.ge(rewardRateCol, minRate));
                }

                if (maxRate != null && BigDecimal.ZERO.compareTo(maxRate) < 0) {
                    predicateList.add(cb.le(rewardRateCol, maxRate));
                }
                if (idList != null && idList.size() > 0) {
                    predicateList.add(idCol.in(idList));
                }
                query.where(predicateList.toArray(new Predicate[0]));
                return null;
            }
        };

        Page<Product> all = productRepository.findAll(specification, pageable);
        return all;
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

        //Assert.notNull(product.getId(), "产品编号不能为空");
        Assert.notNull(product.getId(), ErrorEnum.ID_NOT_NULL.getCode());

        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate())<0,
                "收益率范围错误");
    }
}
