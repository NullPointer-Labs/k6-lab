package com.pointerquarkus.controller;

import com.pointerquarkus.model.Transaction;
import com.pointerquarkus.service.TransactionService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/api/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class TransactionResource {

    private static final Logger log = LoggerFactory.getLogger(TransactionResource.class);
    private final TransactionService transactionService;

    @POST
    public Response create(Transaction transaction) {
        log.info("Received request to process new transaction for AccountID: {}", transaction.getAccountId());
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return Response.status(Response.Status.CREATED).entity(createdTransaction).build();
    }

    @GET
    public Response findAll() {
        log.info("Received request to retrieve all transactions.");
        List<Transaction> transactions = transactionService.getAllTransactions();
        return Response.ok(transactions).build();
    }
}