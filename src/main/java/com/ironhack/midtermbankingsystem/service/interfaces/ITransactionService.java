package com.ironhack.midtermbankingsystem.service.interfaces;


import com.ironhack.midtermbankingsystem.DTO.TransactionDTO;
import com.ironhack.midtermbankingsystem.model.Transaction;

import java.util.List;

public interface ITransactionService {
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(Integer id);
    Transaction createTransaction(TransactionDTO dto);
    Transaction createThirdPartyTransaction(TransactionDTO dto);
    void updateTransaction(Integer id, Transaction transaction);
    void deleteTransaction(Integer id);
}
