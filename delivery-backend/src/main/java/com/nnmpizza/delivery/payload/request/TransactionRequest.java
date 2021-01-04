package com.nnmpizza.delivery.payload.request;

import java.util.Set;

public class TransactionRequest {

    private Long memberID;

    private Set<Long> products;

    public Set<Long> getProducts() {
        return products;
    }

    public void setProducts(Set<Long> products) {
        this.products = products;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }
}
