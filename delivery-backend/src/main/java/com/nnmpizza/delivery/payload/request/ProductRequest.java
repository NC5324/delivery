package com.nnmpizza.delivery.payload.request;

import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.repository.ProductRepository;

import java.util.Set;

public class ProductRequest {

    private String name;
    private String type;
    private Long id;
    private Double price;
    private Set<Long> toppings;

    public Set<Long> getToppings() {
        return toppings;
    }

    public void setToppings(Set<Long> toppings) {
        this.toppings = toppings;
    }

    public ProductRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
