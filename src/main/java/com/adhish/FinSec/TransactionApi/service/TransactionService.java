package com.adhish.FinSec.TransactionApi.service;

import com.adhish.FinSec.Entity.User;
import com.adhish.FinSec.Repo.UserRepository;
import com.adhish.FinSec.TransactionApi.dto.TransactionApprovalDTO;
import com.adhish.FinSec.TransactionApi.dto.TransactionRequestDTO;
import com.adhish.FinSec.TransactionApi.dto.TransactionResponseDTO;
import com.adhish.FinSec.TransactionApi.entity.Transaction;
import com.adhish.FinSec.TransactionApi.enums.TransactionRiskLevel;
import com.adhish.FinSec.TransactionApi.enums.TransactionStatus;
import com.adhish.FinSec.TransactionApi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;


    public Transaction  initiateTransaction(TransactionRequestDTO request, String username){
        User user = userRepository.findByEmail(username).orElseThrow();

        Transaction tx = new Transaction();
        tx.setFromAccount(request.getFromAccount());
        tx.setToAccount(request.getToAccount());
        tx.setAmount(request.getAmount());
        tx.setForeign(request.isForeign());
        tx.setInitiatedBy(user);
        tx.setInitiatedAt(LocalDateTime.now());
        tx.setStatus(TransactionStatus.PENDING);

        if(request.getAmount() > 50000 || request.isForeign()){
            tx.setRiskLevel(TransactionRiskLevel.RISKY);
        }else {
            tx.setRiskLevel(TransactionRiskLevel.NORMAL);
        }

        return transactionRepository.save(tx);
    }

    //To Approve Transaction

    public Transaction approveTransaction(TransactionApprovalDTO dto, String role){

        Transaction tx = transactionRepository.findById(dto.transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        // If the transaction is risky and the role is Teller, return with PENDING status
        if (tx.getRiskLevel() == TransactionRiskLevel.RISKY && role.equals("ROLE_TELLER")) {
            tx.setStatus(TransactionStatus.PENDING);  // Set status to PENDING for risky transactions by Teller
            return transactionRepository.save(tx);  // Return the transaction without approval
        }

        // If the role is Manager, they can approve any transaction, no matter the risk level
        if (role.equals("ROLE_MANAGER")) {
            tx.setStatus(dto.approve ? TransactionStatus.APPROVED : TransactionStatus.REJECTED);
            return transactionRepository.save(tx);  // Manager can approve or reject any transaction
        }

        tx.setStatus(dto.approve ? TransactionStatus.APPROVED : TransactionStatus.REJECTED);
        return transactionRepository.save(tx);
    }

    public List<Transaction> getPendingTransactions() {
        return transactionRepository.findByStatus(TransactionStatus.PENDING);
    }

    public TransactionResponseDTO convertToDto(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(transaction.getId());
        dto.setFromAccount(transaction.getFromAccount());
        dto.setToAccount(transaction.getToAccount());
        dto.setAmount(transaction.getAmount());
        dto.setForeign(transaction.isForeign());
        dto.setStatus(transaction.getStatus());
        dto.setRiskLevel(transaction.getRiskLevel());
        dto.setInitiatedAt(transaction.getInitiatedAt());
        if (transaction.getInitiatedBy() != null) {
            dto.setInitiatedByName(transaction.getInitiatedBy().getName());
            dto.setInitiatedByEmail(transaction.getInitiatedBy().getEmail());
        }
        return dto;
    }

}
