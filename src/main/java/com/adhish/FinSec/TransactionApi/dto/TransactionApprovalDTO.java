package com.adhish.FinSec.TransactionApi.dto;

public class TransactionApprovalDTO {
    public long transactionId;
    public boolean approve;



    //S and G


    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }
}
