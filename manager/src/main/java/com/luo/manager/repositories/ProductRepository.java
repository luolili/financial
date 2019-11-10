package com.luo.manager.repositories;

import com.luo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * boot2 ：findOne方法报错，增加CrudRepository
 */
public interface ProductRepository extends CrudRepository<Product, String>, JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

}
