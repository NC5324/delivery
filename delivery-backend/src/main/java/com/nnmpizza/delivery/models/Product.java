package com.nnmpizza.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nnmpizza.delivery.payload.request.ProductRequest;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private Double price;

    @Column(name = "img_source")
    private String imgSource;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "product_topping",
            joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name = "topping_id"))
    private Set<Topping> toppings = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Order> orders = new HashSet<>();

    public Product(Long id, String name, String type, Double price, String imgSource) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.imgSource = imgSource;
    }

    public Product(ProductRequest productRequest){
        this.id = productRequest.getId();
        this.name = productRequest.getName();
        this.type = productRequest.getType();
        this.price = productRequest.getPrice();
    }

    public Product () {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public Long getId() {
        return id;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(Set<Topping> toppings) {
        this.toppings = toppings;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
