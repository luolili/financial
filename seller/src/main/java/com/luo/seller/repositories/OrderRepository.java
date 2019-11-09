package com.luo.seller.repositories;

import com.luo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, String>,
        JpaSpecificationExecutor<Order> {


}
