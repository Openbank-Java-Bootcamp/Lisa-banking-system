package com.example.midtermbankingsystem.service.interfaces;


import com.example.midtermbankingsystem.DTO.TransactionDTO;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(Integer id);
    Transaction createTransaction(TransactionDTO transactionDTO);
    void applyTransaction(BigDecimal amount, Optional<Account> payer, Optional<Account> target);
    void updateTransaction(Integer id, Transaction transaction);
    void deleteTransaction(Integer id);
}
