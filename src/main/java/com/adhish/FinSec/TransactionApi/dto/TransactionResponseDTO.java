package com.adhish.FinSec.TransactionApi.dto;

import com.adhish.FinSec.TransactionApi.enums.TransactionRiskLevel;
import com.adhish.FinSec.TransactionApi.enums.TransactionStatus;

import java.time.LocalDateTime;

public class TransactionResponseDTO {
    private Long id;
    private String fromAccount;
    private String toAccount;
    private Double amount;
    private boolean isForeign;
    private TransactionStatus status;
    private TransactionRiskLevel riskLevel;
    private String initiatedByEmail;
    private String initiatedByName;
    private LocalDateTime initiatedAt;










    //Setters and Getters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isForeign() {
        return isForeign;
    }

    public void setForeign(boolean foreign) {
        isForeign = foreign;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionRiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(TransactionRiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getInitiatedByEmail() {
        return initiatedByEmail;
    }

    public void setInitiatedByEmail(String initiatedByEmail) {
        this.initiatedByEmail = initiatedByEmail;
    }

    public String getInitiatedByName() {
        return initiatedByName;
    }

    public void setInitiatedByName(String initiatedByName) {
        this.initiatedByName = initiatedByName;
    }

    public LocalDateTime getInitiatedAt() {
        return initiatedAt;
    }

    public void setInitiatedAt(LocalDateTime initiatedAt) {
        this.initiatedAt = initiatedAt;
    }
}