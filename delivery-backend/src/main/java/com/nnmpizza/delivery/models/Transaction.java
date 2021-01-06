package com.nnmpizza.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nnmpizza.delivery.payload.request.TransactionRequest;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "transaction")
    private Set<Order> orders = new HashSet<>();

    public Transaction() {

    }

    public Transaction(Member member){
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
