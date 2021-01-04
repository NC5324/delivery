package com.nnmpizza.delivery.payload.request;

import com.nnmpizza.delivery.models.Product;
import com.nnmpizza.delivery.repository.ProductRepository;

public class ProductRequest {

    private String name;
    private String description;
    private String type;
    private Long id;
    private Double price;

    public ProductRequest(String name, String description, String type, Double price) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
    }

    public ProductRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
