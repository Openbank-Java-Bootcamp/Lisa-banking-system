package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.model.CheckingAccount;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.CheckingAccountRepository;
import com.example.midtermbankingsystem.service.interfaces.ICheckingAccountService;
import com.example.midtermbankingsystem.utils.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    public CheckingAccount getCheckingAccountById(Integer id) {
        Optional<CheckingAccount> foundCheckingAccount = checkingAccountRepository.findById(id);
        if (foundCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Checking Account found with that ID");
        } else {
            return foundCheckingAccount.get();
        }
    }

    public CheckingAccount saveCheckingAccount(CheckingAccountDTO checkingAccountDTO) {
        log.info(Color.YELLOW_BOLD_BRIGHT + "Saving a new account {} inside of the database" + Color.RESET, checkingAccountDTO);

        Optional<AccountHolder> foundAccountHolder = null;
        Optional<AccountHolder> foundSecondAccountHolder = null;
        CheckingAccount checkingAccount = null;

        if (checkingAccountDTO.getSecondaryOwner() == null) {
            foundAccountHolder = accountHolderRepository.findById(checkingAccountDTO.getPrimaryOwner());
            checkingAccount = CheckingAccount.fromDTOPrimaryOwner(checkingAccountDTO, foundAccountHolder.get());
            log.info(Color.YELLOW_BOLD_BRIGHT + "Saving a new account {} inside of the database" + Color.RESET, checkingAccountDTO);
            foundAccountHolder.get().getPrimaryAccountList().add(checkingAccount);
            accountHolderRepository.save(foundAccountHolder.get());

        }
        else {
            foundAccountHolder = accountHolderRepository.findById(checkingAccountDTO.getPrimaryOwner());
            foundSecondAccountHolder = accountHolderRepository.findById(checkingAccountDTO.getSecondaryOwner());
            checkingAccount = CheckingAccount.fromDTO(checkingAccountDTO, foundAccountHolder.get(), foundSecondAccountHolder.get());

            foundAccountHolder.get().getPrimaryAccountList().add(checkingAccount);
            foundSecondAccountHolder.get().getSecondaryAccountList().add(checkingAccount);
            accountHolderRepository.saveAll(List.of(foundAccountHolder.get(), foundSecondAccountHolder.get()));
        }
        

        if (checkingAccountDTO.getPrimaryOwner() != null && foundAccountHolder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID passed to Primary Owner");
        }
        if (checkingAccountDTO.getSecondaryOwner() != null && foundSecondAccountHolder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID passed to Secondary Owner");
        }

        log.info(Color.YELLOW_BOLD_BRIGHT + "Saving a ACCOUNT {} inside of the database" + Color.RESET, checkingAccount);

        try {
            return checkingAccountRepository.save(checkingAccount);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Checking Account");
        }
    }

    public void updateCheckingAccount(Integer id, CheckingAccount checkingAccount) {

    }

    public void deleteCheckingAccount(Integer id) {
        Optional<CheckingAccount> foundCheckingAccount = checkingAccountRepository.findById(id);
        if (foundCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        }
        checkingAccountRepository.delete(foundCheckingAccount.get());
    }
}
