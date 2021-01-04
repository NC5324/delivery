package com.nnmpizza.delivery.models;

import com.nnmpizza.delivery.payload.request.TransactionRequest;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    public Transaction(Member member) {
        this.member = member;
    }

    public Transaction() {

    }

    public Transaction(TransactionRequest transactionRequest) {
        id = transactionRequest.getId();
        member.id = transactionRequest.getMemberID();
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
}
