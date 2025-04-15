package com.adhish.FinSec.Model;

import com.adhish.FinSec.Enum.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String toAccount;

    private Double amount;

    private Boolean isInternational = false;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;  // PENDING, APPROVED, RISKY, REJECTED

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User user;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<Approval> approvals;
}

