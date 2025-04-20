package com.adhish.FinSec.TransactionApi.repository;

import com.adhish.FinSec.TransactionApi.entity.Transaction;
import com.adhish.FinSec.TransactionApi.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByStatus(TransactionStatus status);
}
