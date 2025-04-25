package com.adhish.FinSec.TransactionApi.dto;

import com.adhish.FinSec.TransactionApi.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
    private Long id;
    private String fromAccount;
    private String toAccount;
    private Double amount;
    private boolean isForeign;
    private String status;
    private String riskLevel;
    private String initiatedByEmail;
    private LocalDateTime initiatedAt;


    // constructor from Transaction entity
    @JsonCreator
    public TransactionDTO(Transaction t) {
        this.id = t.getId();
        this.fromAccount = t.getFromAccount();
        this.toAccount = t.getToAccount();
        this.amount = t.getAmount();
        this.isForeign = t.isForeign();
        this.status = t.getStatus().name();
        this.riskLevel = t.getRiskLevel().name();
        this.initiatedByEmail = (t.getInitiatedBy() != null) ? t.getInitiatedBy().getEmail() : "Unknown";
        this.initiatedAt = t.getInitiatedAt();
    }

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

    public String getInitiatedByEmail() {
        return initiatedByEmail;
    }

    public void setInitiatedByEmail(String initiatedByEmail) {
        this.initiatedByEmail = initiatedByEmail;
    }

    public LocalDateTime getInitiatedAt() {
        return initiatedAt;
    }

    public void setInitiatedAt(LocalDateTime initiatedAt) {
        this.initiatedAt = initiatedAt;
    }
}
