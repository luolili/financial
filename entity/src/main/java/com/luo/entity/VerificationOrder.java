package com.luo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "verification_order")
public class VerificationOrder {
    @Id
    private String orderId;
    private String chanId;
    private String chanUserId;
    private String orderType;
    private String productId;
    private String outerOrderId;
    private BigDecimal amount;
    private String memo;
    private String createUser;
    private String updateUser;
    // 解决swagger 传时间的问题
    @JsonFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    private Date createAt;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChanId() {
        return chanId;
    }

    public void setChanId(String chanId) {
        this.chanId = chanId;
    }

    public String getChanUserId() {
        return chanUserId;
    }

    public void setChanUserId(String chanUserId) {
        this.chanUserId = chanUserId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOuterOrderId() {
        return outerOrderId;
    }

    public void setOuterOrderId(String outerOrderId) {
        this.outerOrderId = outerOrderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "VerificationOrder{" +
                "orderId='" + orderId + '\'' +
                ", chanId='" + chanId + '\'' +
                ", chanUserId='" + chanUserId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", productId='" + productId + '\'' +
                ", outerOrderId='" + outerOrderId + '\'' +
                ", amount=" + amount +
                ", memo='" + memo + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}