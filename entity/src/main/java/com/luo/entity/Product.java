package com.luo.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private String id;
    private String name;
    private String status;
    private BigDecimal thresholdAmount;
    private BigDecimal stepAmount;
    private Integer lockTerm;
    private BigDecimal rewardRate;//收益率
    private String memo;
    private String createUser;
    private String updateUser;
    private Date createAt;
    private Date updateAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(BigDecimal thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public BigDecimal getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(BigDecimal stepAmount) {
        this.stepAmount = stepAmount;
    }

    public Integer getLockTerm() {
        return lockTerm;
    }

    public void setLockTerm(Integer lockTerm) {
        this.lockTerm = lockTerm;
    }

    public BigDecimal getRewardRate() {
        return rewardRate;
    }

    public void setRewardRate(BigDecimal rewardRate) {
        this.rewardRate = rewardRate;
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

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", thresholdAmount=" + thresholdAmount +
                ", stepAmount=" + stepAmount +
                ", lockTerm=" + lockTerm +
                ", rewardRate=" + rewardRate +
                ", memo='" + memo + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
