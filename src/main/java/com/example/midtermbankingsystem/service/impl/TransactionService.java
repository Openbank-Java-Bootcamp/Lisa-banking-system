package com.example.midtermbankingsystem.service.impl;


import com.example.midtermbankingsystem.DTO.AccountStatusDTO;
import com.example.midtermbankingsystem.DTO.TransactionDTO;
import com.example.midtermbankingsystem.enums.Status;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.Money;
import com.example.midtermbankingsystem.model.Transaction;
import com.example.midtermbankingsystem.repository.*;
import com.example.midtermbankingsystem.service.interfaces.ITransactionService;
import com.example.midtermbankingsystem.utils.Color;
import com.example.midtermbankingsystem.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
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
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private Utils utils;


    public List<Transaction> getAllTransactions() {
        return null;
    }

    public Transaction getTransactionById(Integer id) {
        return null;
    }

    public Transaction createTransaction(TransactionDTO dto) {
        var payerAccount = dto.getPayerAccId() != null ? Optional.of(accountService.getAccountById(dto.getPayerAccId())) : null;
        var targetAccount = dto.getTargetAccId() != null ? Optional.of(accountService.getAccountById(dto.getTargetAccId())) : null;
        var transaction = Transaction.fromDTO(dto, payerAccount, targetAccount);

        return processTransaction(payerAccount, targetAccount, transaction);
    }

    public Transaction createThirdPartyTransaction(TransactionDTO dto) {
        Optional<Account> payerAccount = null;
        var targetAccount = dto.getTargetAccId() != null ? Optional.of(accountService.getAccountById(dto.getTargetAccId())) : null;
        var transaction = Transaction.fromDTO(dto, payerAccount, targetAccount);

        return processTransaction(payerAccount, targetAccount, transaction);
    }

    public Transaction processTransaction(Optional<Account> payerAccount, Optional<Account> targetAccount, Transaction transaction) {

        if (payerAccount == null && targetAccount == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST
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

        if (payer != null) {

            validateTimeSinceLastTransactions(payer.get());

            validateNotOverDailyLimit(payer.get(), transaction);

            utils.validateLoggedUserIsAccOwner(payer.get());

            validateFunds(transaction, payer.get());

            validateAccountStatus(payer.get());
        }

        if (target != null) {
            validateTargetName(transaction, target.get());

            validateAccountStatus(target.get());
        }

        if (transaction.getPayerThirdPartyAcc() != null) {

            if (transaction.getSecretKey() != null) {
                if (target != null) validateSecretKey(transaction.getSecretKey(), target.get());

            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "Transaction failed, Third Parties must provide Target Account Secret Key");
        }

        if (transaction.getTargetThirdPartyAcc() != null) {
            validateThirdPartyTargetName(transaction, transaction.getTargetThirdPartyAcc());
        }

    }

    private void validateAccountStatus(Account account) {
        //validating account isn't frozen
        if (account.getStatus().equals(Status.FROZEN)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                , "Transaction failed, Account is frozen");
    }

    private void validateFunds(Transaction transaction, Account payer) {
        //validating sufficient funds
        BigDecimal payerBalance = payer.getBalance().getAmount();
        BigDecimal amount = transaction.getAmount().getAmount();

        if (amount.compareTo(payerBalance) > 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                , "Transaction failed, insufficient funds");
    }

    private void validateTargetName(Transaction transaction, Account target) {

        String transactionTargetName = transaction.getTargetName();

        String targetPrimaryName = target.getPrimaryOwner().getName();

        //in case we don't have a secondary name
        var targetSecondaryName = target.getSecondaryOwner() != null
                ? target.getSecondaryOwner().getName() : null;


        if (targetSecondaryName == null ? !targetPrimaryName.equals(transactionTargetName) :
                !targetPrimaryName.equals(transactionTargetName) && !targetSecondaryName.equals(transactionTargetName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "Transaction failed, invalid Target Name");
        }
    }

    public void validateThirdPartyTargetName(Transaction transaction, Integer targetAcc) {

        String transactionTargetName = transaction.getTargetName();

        var targetAccount = thirdPartyRepository.findByThirdPartyAccount(targetAcc)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account found with ID " + targetAcc));

        String targetName = targetAccount.getName();

        if (!transactionTargetName.equals(targetName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "Transaction failed, invalid Target Name");
        }
    }

    public void validateSecretKey(String secretKey, Account target) {

        if (!target.getSecretKey().equals(secretKey)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "Transaction failed, invalid Secret Key");
        }
    }


    public void applyTransaction(BigDecimal amount, Optional<Account> payer, Optional<Account> target) {

        if (payer != null) {
            var payerBalance = payer.get().getBalance().getAmount();
            payer.get().setBalance(new Money(payerBalance.subtract(amount), payer.get().getBalance().getCurrency()));
            accountRepository.save(payer.get());
        }

        if (target != null) {
            var targetBalance = target.get().getBalance().getAmount();
            target.get().setBalance(new Money(targetBalance.add(amount), target.get().getBalance().getCurrency()));
            accountRepository.save(target.get());
        }
    }


    public void applyPenaltyFee(Optional<Account> payer, Optional<Account> target) {
        if (payer != null) findMinimumBalanceAndApplyFee(payer.get());
        if (target != null) findMinimumBalanceAndApplyFee(target.get());
    }

    private void findMinimumBalanceAndApplyFee(Account account) {

        String accountType = account.getClass().toString();

        var balance = account.getBalance().getAmount();
        var currency = account.getBalance().getCurrency();


        if (accountType.contains(".SavingsAccount")) {
            var targetSavingsAcc = savingsAccountRepository.findById(account.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Savings Account found with ID " + account.getId()));

            if (targetSavingsAcc.getBalance().getAmount().compareTo(targetSavingsAcc.getMinimumBalance().getAmount()) == -1) {

                targetSavingsAcc.setBalance(new Money(balance.subtract(new BigDecimal(40)), currency));

                savingsAccountRepository.save(targetSavingsAcc);

                log.info(Color.YELLOW_BOLD_BRIGHT + "Savings Account balance: {} below minimum balance : {} Applying Penalty Fee"
                        + Color.RESET, balance, targetSavingsAcc.getMinimumBalance().getAmount());

            }

        }
        if (accountType.contains(".CheckingAccount")) {
            var targetCheckingAcc = checkingAccountRepository.findById(account.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Savings Account found with ID " + account.getId()));

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

    public void validateTimeSinceLastTransactions(Account payer) {

        var accountList = transactionRepository.findByPayerAccOrderByTransferDate(payer);

        if (accountList.size() > 0) {

            var mostRecentAccountTime = accountList.get(accountList.size() - 1).getTransferDate();

            var now = Instant.now();

            var duration = now.toEpochMilli() - mostRecentAccountTime.toEpochMilli();

            if (duration < 1000) {

                accountService.changeAccountStatus(payer.getId(), new AccountStatusDTO(Status.FROZEN));

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                        , "Transaction failed, fraud detected as your last transaction was {}" + duration +
                        " seconds ago. Account frozen");
            }

        }
    }

    public void validateNotOverDailyLimit(Account payer, Transaction transaction) {

        var maxDailyAmount = transactionRepository.findHighestDailyTransactionByAccount(payer);

        if (maxDailyAmount != null) {

            var max = maxDailyAmount.multiply(BigDecimal.valueOf(1.5));
            var todayAmount = transactionRepository.findTodayTotalTransactions(payer);
            var todayTotalAmount = todayAmount == null ? transaction.getAmount().getAmount()
                    : todayAmount.add(transaction.getAmount().getAmount());

            log.info(Color.YELLOW_BOLD_BRIGHT + "Accounts max daily amount is {} times 1.5 {}"
                    + Color.RESET, maxDailyAmount, max);
            log.info(Color.YELLOW_BOLD_BRIGHT + "Accounts today's amount is {} plus transaction trying to make {}"
                    + Color.RESET, todayAmount, todayTotalAmount);

            if (todayTotalAmount.compareTo(max) > 0 && todayAmount.compareTo(BigDecimal.valueOf(500)) > 0) {

                accountService.changeAccountStatus(payer.getId(), new AccountStatusDTO(Status.FROZEN));

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                        , "Transaction failed, fraud detected as transactions in the las 24 hours are higher than 150% of" +
                        " your usual daily transactions. Account frozen");
            }
        }
    }

}
