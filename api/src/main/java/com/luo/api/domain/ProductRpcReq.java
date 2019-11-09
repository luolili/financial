package com.luo.api.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public class ProductRpcReq {


    private List<String> idList;
    BigDecimal minRewardRate;
    BigDecimal maxRewardRate;
    List<String> statusList;
    private int page;
    private int pageSize;
    private Sort.Direction direction;
    private String orderBy;
    // Pageable pageable;

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public BigDecimal getMinRewardRate() {
        return minRewardRate;
    }

    public void setMinRewardRate(BigDecimal minRewardRate) {
        this.minRewardRate = minRewardRate;
    }

    public BigDecimal getMaxRewardRate() {
        return maxRewardRate;
    }

    public void setMaxRewardRate(BigDecimal maxRewardRate) {
        this.maxRewardRate = maxRewardRate;
    }


    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "ProductRpcReq{" +
                "idList=" + idList +
                ", minRewardRate=" + minRewardRate +
                ", maxRewardRate=" + maxRewardRate +
                ", statusList=" + statusList +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", direction=" + direction +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }
}
