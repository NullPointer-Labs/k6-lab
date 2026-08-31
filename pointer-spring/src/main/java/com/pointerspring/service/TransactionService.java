package com.pointerspring.service;

import com.pointerspring.model.Transaction;
import com.pointerspring.repo.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        transaction.setCreatedAt(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(transaction);
        log.debug("Created transaction successfully. ID: {}, AccountID: {}",
                savedTransaction.getId(), savedTransaction.getAccountId());
        return savedTransaction;
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        log.debug("Fetching all transactions from the database.");
        return transactionRepository.findAll();
    }
}