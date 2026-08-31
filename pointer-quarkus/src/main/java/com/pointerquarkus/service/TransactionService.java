package com.pointerquarkus.service;

import com.pointerquarkus.model.Transaction;
import com.pointerquarkus.repository.TransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.persist(transaction);
        log.debug("Created transaction successfully. ID: {}, AccountID: {}",
                transaction.getId(), transaction.getAccountId());
        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        log.debug("Fetching all transactions from the database.");
        return transactionRepository.listAll();
    }
}