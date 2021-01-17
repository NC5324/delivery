package com.nnmpizza.delivery.payload.request;

import com.nnmpizza.delivery.payload.beans.OrderItemBean;
import com.nnmpizza.delivery.payload.beans.ProductQuantity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderRequest {
    private Long id;
    private Long memberId;
    private Set<OrderItemBean> orderItems;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<OrderItemBean> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemBean> orderItems) {
        this.orderItems = orderItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
