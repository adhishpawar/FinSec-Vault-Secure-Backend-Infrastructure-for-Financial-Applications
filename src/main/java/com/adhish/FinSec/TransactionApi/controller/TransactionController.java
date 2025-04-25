package com.adhish.FinSec.TransactionApi.controller;

import com.adhish.FinSec.TransactionApi.dto.TransactionApprovalDTO;
import com.adhish.FinSec.TransactionApi.dto.TransactionDTO;
import com.adhish.FinSec.TransactionApi.dto.TransactionRequestDTO;
import com.adhish.FinSec.TransactionApi.entity.Transaction;
import com.adhish.FinSec.TransactionApi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private  TransactionService transactionService;

    @PostMapping("/initiate")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> initiate(@RequestBody TransactionRequestDTO dto,
                                                                   Principal principal){
        Transaction tx = transactionService.initiateTransaction(dto,principal.getName());

        return ResponseEntity.ok(transactionService.convertToDto(tx));
    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_TELLER')")
    public ResponseEntity<?> approve(@RequestBody TransactionApprovalDTO dto, Principal principal, Authentication auth) {
        String role = auth.getAuthorities().iterator().next().getAuthority();
        System.out.println("Role:" + role);
        Transaction tx = transactionService.approveTransaction(dto,role);
        return ResponseEntity.ok(transactionService.convertToDto(tx));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_TELLER')")
    public ResponseEntity<?> getPending() {
        List<Transaction> transactions = transactionService.getPendingTransactions();
        List<TransactionDTO> dtos = transactions.stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
