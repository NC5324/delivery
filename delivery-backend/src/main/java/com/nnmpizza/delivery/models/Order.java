package com.nnmpizza.delivery.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberNoAuthDetails member;

    @ManyToMany
    //@JsonIgnore
    @JoinTable(name = "order_orderitem",
            joinColumns = @JoinColumn(name="order_id"),
            inverseJoinColumns = @JoinColumn(name = "orderitem_id"))
    private Set<OrderItem> orderItems = new HashSet<>();

    public Order() { }

    public Order(Long id, MemberNoAuthDetails member, String status) {
        this.id = id;
        this.member = member;
        this.status = status;
    }

    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public MemberNoAuthDetails getMember() {
        return member;
    }

    public void setMember(MemberNoAuthDetails member) {
        this.member = member;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
