package com.luo.seller.repobackup;

import com.luo.entity.VerificationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface VerifyRepository extends JpaRepository<VerificationOrder, String>,
        JpaSpecificationExecutor<VerificationOrder> {

    @Query(value = "select concat_ws('|',order_id,outer_order_id,chan_id,chan_user_id,order_type,amount,date_format( create_at,'%Y-%m-%d %H:%i:%s')) from verification_order where chan_id=?1 and create_at>=?2 and create_at<?3", nativeQuery = true)
    List<String> queryVerificationOrders(String chanId, Date start, Date end);
}
