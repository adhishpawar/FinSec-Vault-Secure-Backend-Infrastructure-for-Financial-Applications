package com.adhish.FinSec.TransactionApi.service;

import com.adhish.FinSec.Entity.User;
import com.adhish.FinSec.TransactionApi.entity.TransactionStatus;
import com.adhish.FinSec.Repo.UserRepository;
import com.adhish.FinSec.TransactionApi.dto.TransactionRequest;
import com.adhish.FinSec.TransactionApi.dto.TransactionResponse;
import com.adhish.FinSec.TransactionApi.entity.Transaction;
import com.adhish.FinSec.TransactionApi.enums.TransactionRiskLevel;
import com.adhish.FinSec.TransactionApi.repository.TransactionRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

//    @Autowired
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public TransactionResponse initiate(TransactionRequest request,String username){
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

        Transaction saved = transactionRepository.save(tx);

        return new TransactionResponse(
                saved.getId(),
                saved.getStatus().toString(),
                saved.getRiskLevel().toString()
        );
    }

    //To Approve Transaction
    public String approveTransaction(Long transactionId, String approverEmail){

        Transaction tx = transactionRepository.findById(transactionId).orElseThrow();
        User approver = userRepository.findByEmail(approverEmail).orElseThrow();

        boolean isManager = approver.getRole().toString().equals("ROLE_MANAGER");

        boolean isTeller = approver.getRole().toString().equals("ROLE_TELLER");

        if(tx.getRiskLevel() == TransactionRiskLevel.RISKY && !isManager){
            throw new AccessDeniedException("Only MANAGER can approve risky transactions");
        }
        if (tx.getRiskLevel() == TransactionRiskLevel.NORMAL && !isTeller) {
            throw new AccessDeniedException("Only TELLER can approve normal transactions");
        }

        tx.setStatus(TransactionStatus.APPROVED);
        transactionRepository.save(tx);

        return "Transaction approved successfully";
    }

}
