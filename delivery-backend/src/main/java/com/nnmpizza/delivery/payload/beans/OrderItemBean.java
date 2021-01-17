package com.nnmpizza.delivery.payload.beans;

import com.nnmpizza.delivery.models.Member;
import com.nnmpizza.delivery.models.Product;

public class OrderItemBean {

    private Long id;
    private Product product;
    private Long quantity;

    public Product getProduct() {
        return product;
    }

    public void setProductId(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
