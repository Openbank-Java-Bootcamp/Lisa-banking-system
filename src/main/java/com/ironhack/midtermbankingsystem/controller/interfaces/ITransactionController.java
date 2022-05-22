package com.ironhack.midtermbankingsystem.controller.interfaces;

import com.ironhack.midtermbankingsystem.DTO.TransactionDTO;
import com.ironhack.midtermbankingsystem.model.Transaction;
import org.springframework.http.HttpHeaders;

public interface ITransactionController {
    Transaction saveTransaction(TransactionDTO transactionDTO);
    Transaction saveThirdPartyTransaction(HttpHeaders headers, TransactionDTO transactionDTO);
}
