package com.nnmpizza.delivery.payload.request;

public class TransactionRequest {
    private Long id;
    private Long member_id;

    public TransactionRequest(Long id, Long member_id) {
        this.id = id;
        this.member_id = member_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberID() {
        return member_id;
    }

    public void setMemberID(Long member_id) {
        this.member_id = member_id;
    }
}
