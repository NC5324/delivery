package com.nnmpizza.delivery.payload.beans;

import com.nnmpizza.delivery.models.Member;
import com.nnmpizza.delivery.models.Product;

public class OrderItemPojo {

    private Long id;
    private Long productId;
    private Long quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
