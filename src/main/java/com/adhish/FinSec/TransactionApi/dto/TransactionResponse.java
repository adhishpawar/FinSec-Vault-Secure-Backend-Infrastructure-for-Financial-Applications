package com.adhish.FinSec.TransactionApi.dto;

public class TransactionResponse {
    private Long transactionId;
    private String status;
    private String riskLevel;

    public TransactionResponse(Long transactionId, String status, String riskLevel) {
        this.transactionId = transactionId;
        this.status = status;
        this.riskLevel = riskLevel;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
