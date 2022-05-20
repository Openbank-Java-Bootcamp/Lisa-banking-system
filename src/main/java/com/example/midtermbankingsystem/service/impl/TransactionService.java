package com.example.midtermbankingsystem.service.impl;


import com.example.midtermbankingsystem.DTO.TransactionDTO;
import com.example.midtermbankingsystem.enums.Status;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.Money;
import com.example.midtermbankingsystem.model.Transaction;
import com.example.midtermbankingsystem.repository.AccountRepository;
import com.example.midtermbankingsystem.repository.CheckingAccountRepository;
import com.example.midtermbankingsystem.repository.SavingsAccountRepository;
import com.example.midtermbankingsystem.repository.TransactionRepository;
import com.example.midtermbankingsystem.service.interfaces.ITransactionService;
import com.example.midtermbankingsystem.utils.Color;
import com.example.midtermbankingsystem.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private SavingsAccountService savingsAccountService;

    @Autowired
    private CheckingAccountService checkingAccountService;

    @Autowired
    private Utils utils;


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


        applyPenaltyFee(payerAccount, targetAccount);
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

            utils.validateLoggedUserIsAccOwner(payer.get());

            validateFunds(transaction, payer.get());

            validateAccountStatus(payer.get());
        }

        if (target.isPresent()) {

            validateTargetName(transaction, target.get());

            validateAccountStatus(target.get());
        }

    }


    private void validateAccountStatus(Account account) {
        //validating account isn't frozen
        if (account.getStatus().equals(Status.FROZEN)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                , "Transaction failed, Payer Account is frozen");
    }

    private void validateFunds(Transaction transaction, Account payer) {
        //validating sufficient funds
        BigDecimal payerBalance = payer.getBalance().getAmount();
        BigDecimal amount = transaction.getAmount().getAmount();

        if (amount.compareTo(payerBalance) == 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                , "Transaction failed, insufficient funds");
    }

    private void validateTargetName(Transaction transaction, Account target) {

        String transactionTargetName = transaction.getTargetName();

        var targetPrimaryName = target.getPrimaryOwner().getName();

        //in case we don't have a secondary name
        var targetSecondaryName = target.getSecondaryOwner() != null
                ? target.getSecondaryOwner().getName() : null;


        if (targetSecondaryName == null ? !targetPrimaryName.equals(transactionTargetName) :
                !targetPrimaryName.equals(transactionTargetName) && !targetSecondaryName.equals(transactionTargetName)) {
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


    public void applyPenaltyFee(Optional<Account> payer, Optional<Account> target) {
        if (payer.isPresent()) findMinimumBalanceAndApplyFee(payer.get());
        if (target.isPresent()) findMinimumBalanceAndApplyFee(target.get());
    }

    private void findMinimumBalanceAndApplyFee(Account account) {

        String accountType = account.getClass().toString();

        var balance = account.getBalance().getAmount();
        var currency = account.getBalance().getCurrency();


        if (accountType.contains(".SavingsAccount")) {
            var targetSavingsAcc = savingsAccountService.getSavingsAccountById(account.getId());

            if (targetSavingsAcc.getBalance().getAmount().compareTo(targetSavingsAcc.getMinimumBalance().getAmount()) == -1) {

                targetSavingsAcc.setBalance(new Money(balance.subtract(new BigDecimal(40)), currency));

                savingsAccountRepository.save(targetSavingsAcc);

                log.info(Color.YELLOW_BOLD_BRIGHT + "Savings Account balance: {} below minimum balance : {} Applying Penalty Fee"
                        + Color.RESET, balance, targetSavingsAcc.getMinimumBalance().getAmount());

            }

        } else if (accountType.contains(".CheckingAccount")) {
            var targetCheckingAcc = checkingAccountService.getCheckingAccountById(account.getId());

            if (targetCheckingAcc.getBalance().getAmount().compareTo(targetCheckingAcc.getMinimumBalance().getAmount()) == -1) {

                targetCheckingAcc.setBalance(new Money(balance.subtract(new BigDecimal(40)), currency));

                checkingAccountRepository.save(targetCheckingAcc);

                log.info(Color.YELLOW_BOLD_BRIGHT + "Checking Account balance: {} below minimum balance : {} Applying Penalty Fee"
                        + Color.RESET, balance, targetCheckingAcc.getMinimumBalance().getAmount());
            }
        }
    }


    public void updateTransaction(Integer id, Transaction transaction) {

    }

    public void deleteTransaction(Integer id) {

    }


}
