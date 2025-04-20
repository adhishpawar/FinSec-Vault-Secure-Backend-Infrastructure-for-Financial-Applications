package com.adhish.FinSec.Entity;

import com.adhish.FinSec.TransactionApi.entity.Transaction;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean approved;

    private LocalDateTime approvedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;
}

