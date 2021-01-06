package com.nnmpizza.delivery.payload.request;

import com.nnmpizza.delivery.payload.beans.ProductQuantity;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    private Long memberId;
    private List<ProductQuantity> products;
    private String status;

    public Long getMemberId() {
        return memberId;
    }

    public List<ProductQuantity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantity> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
