package com.example.midtermbankingsystem.service.impl;


import com.example.midtermbankingsystem.DTO.TransactionDTO;
import com.example.midtermbankingsystem.enums.Status;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.Money;
import com.example.midtermbankingsystem.model.Transaction;
import com.example.midtermbankingsystem.repository.AccountRepository;
import com.example.midtermbankingsystem.repository.TransactionRepository;
import com.example.midtermbankingsystem.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    public List<Transaction> getAllTransactions() {
        return null;
    }

    public Transaction getTransactionById(Integer id) {
        return null;
    }

    public Transaction createTransaction(TransactionDTO dto) {

        var payerAccount = accountRepository.findById(dto.getPayerAccId());
        var targetAccount = accountRepository.findById(dto.getTargetAccId());
        var transaction = Transaction.fromDTO(dto, payerAccount, targetAccount);


        if (payerAccount.isEmpty() && targetAccount.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                , "Payer or Target Account must be in our banking system");


        validateTransaction(transaction, payerAccount, targetAccount);
        applyTransaction(transaction.getAmount().getAmount(), payerAccount, targetAccount);


        try {
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Transaction");
        }

    }

    public void validateTransaction(Transaction transaction, Optional<Account> payer, Optional<Account> target) {

        if (payer.isPresent()) {

            validateFunds(transaction, payer);

            validateAccountStatus(payer);
        }

        if (target.isPresent()) {

            validateTargetName(transaction, target);

            validateAccountStatus(target);
        }

    }

    private void validateAccountStatus(Optional<Account> account) {
        //validating account isn't frozen
        if(account.get().getStatus().equals(Status.FROZEN)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                , "Transaction failed, Payer Account is frozen");
    }

    private void validateFunds(Transaction transaction, Optional<Account> payer) {
        //validating sufficient funds
        BigDecimal payerBalance = payer.get().getBalance().getAmount();
        BigDecimal amount = transaction.getAmount().getAmount();

        if (amount.compareTo(payerBalance) == 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                , "Transaction failed, insufficient funds");
    }

    private void validateTargetName(Transaction transaction, Optional<Account> target) {

        String transactionTargetName = transaction.getTargetName();
        String targetPrimaryName = target.get().getPrimaryOwner().getName();

        //in case we don't have a secondary name
        var targetSecondaryName = target.get().getSecondaryOwner()!=null
                ? target.get().getSecondaryOwner().getName() : null;

        boolean isPrimaryValid= !targetPrimaryName.equals(transactionTargetName);

        //TODO cant check equals if its null
        boolean isSecondaryValid = !targetSecondaryName.equals(transactionTargetName);


        if (targetSecondaryName == null ? isPrimaryValid: isPrimaryValid && isSecondaryValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "Transaction failed, invalid Target Name");
        }
    }


    public void applyTransaction(BigDecimal amount, Optional<Account> payer, Optional<Account> target) {

        if (payer.isPresent()) {
            var payerBalance = payer.get().getBalance().getAmount();
            payer.get().setBalance(new Money(payerBalance.subtract(amount), payer.get().getBalance().getCurrency()));
            accountRepository.save(payer.get());
        }

        if (target.isPresent()) {
            var targetBalance = target.get().getBalance().getAmount();
            target.get().setBalance(new Money(targetBalance.add(amount), target.get().getBalance().getCurrency()));
            accountRepository.save(target.get());
        }
    }


    public void updateTransaction(Integer id, Transaction transaction) {

    }

    public void deleteTransaction(Integer id) {

    }
}
