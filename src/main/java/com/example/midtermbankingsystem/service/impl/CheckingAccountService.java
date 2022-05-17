package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.model.CheckingAccount;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.CheckingAccountRepository;
import com.example.midtermbankingsystem.service.interfaces.ICheckingAccountService;
import com.example.midtermbankingsystem.utils.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CheckingAccountService implements ICheckingAccountService {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public List<CheckingAccount> getAllCheckingAccounts() {
        List<CheckingAccount> checkingAccountList = checkingAccountRepository.findAll();
        if (checkingAccountList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Checking Accounts found in the database");
        }
        return checkingAccountList;
    }

    public CheckingAccount getCheckingAccountById(String id) {
        Optional<CheckingAccount> foundCheckingAccount = checkingAccountRepository.findById(id);
        if (foundCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Checking Account found with that ID");
        } else {
            return foundCheckingAccount.get();
        }
    }

    public CheckingAccount saveCheckingAccount(CheckingAccount checkingAccount) {
        log.info(Color.YELLOW_BOLD_BRIGHT+"Saving a new account {} inside of the database"+Color.RESET, checkingAccount.toString());
        Optional<AccountHolder> foundAccountHolder = accountHolderRepository.findById(checkingAccount.getPrimaryOwner().getId());
        Optional<AccountHolder> foundSecondAccountHolder = accountHolderRepository.findById(checkingAccount.getSecondaryOwner().getId());

        if (foundAccountHolder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Checking Account found with ID passed in Blog Post");
        }

        try {
            if (foundSecondAccountHolder.isPresent())
                return checkingAccountRepository.save(new CheckingAccount(
                        checkingAccount.getBalance(),
                        checkingAccount.getPrimaryOwner(),
                        checkingAccount.getSecondaryOwner(),
                        checkingAccount.getCreationDate(),
                        checkingAccount.getStatus(),
                        checkingAccount.getSecretKey(),
                        checkingAccount.getMinimumBalance(),
                        checkingAccount.getMonthlyMaintenanceFee()
                ));
            else
                return checkingAccountRepository.save(new CheckingAccount(
                        checkingAccount.getBalance(),
                        checkingAccount.getPrimaryOwner(),
                        checkingAccount.getCreationDate(),
                        checkingAccount.getStatus(),
                        checkingAccount.getSecretKey(),
                        checkingAccount.getMinimumBalance(),
                        checkingAccount.getMonthlyMaintenanceFee()
                ));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Account Holder");
        }
    }

    public void updateCheckingAccount(String id, CheckingAccount checkingAccount) {

    }

    public void deleteCheckingAccount(String id) {
        Optional<CheckingAccount> foundCheckingAccount = checkingAccountRepository.findById(id);
        if (foundCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        }
        checkingAccountRepository.delete(foundCheckingAccount.get());
    }
}
