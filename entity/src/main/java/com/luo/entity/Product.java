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


}
