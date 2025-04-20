package com.adhish.FinSec.TransactionApi.controller;

import com.adhish.FinSec.TransactionApi.dto.TransactionRequest;
import com.adhish.FinSec.TransactionApi.dto.TransactionResponse;
import com.adhish.FinSec.TransactionApi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private  TransactionService transactionService;

    @PostMapping("/initiate")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<TransactionResponse> initiate(@RequestBody TransactionRequest request,
                                                        Principal principal){
        return ResponseEntity.ok(transactionService.initiate(request, principal.getName()));
    }

    @PostMapping("/approve/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_TELLER')")
    public ResponseEntity<String> approve(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(transactionService.approveTransaction(id, principal.getName()));
    }

}
