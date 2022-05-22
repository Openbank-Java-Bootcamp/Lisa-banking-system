package com.ironhack.midtermbankingsystem.service.impl;

import com.ironhack.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.ironhack.midtermbankingsystem.model.Money;
import com.ironhack.midtermbankingsystem.model.SavingsAccount;
import com.ironhack.midtermbankingsystem.repository.AccountHolderRepository;
import com.ironhack.midtermbankingsystem.repository.SavingsAccountRepository;
import com.ironhack.midtermbankingsystem.service.interfaces.ISavingsAccountService;
import com.ironhack.midtermbankingsystem.utils.Color;
import com.ironhack.midtermbankingsystem.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SavingsAccountService implements ISavingsAccountService {

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private Utils utils;


    public List<SavingsAccount> getAllSavingsAccounts() {
        List<SavingsAccount> savingsAccountList = savingsAccountRepository.findAll();
        if (savingsAccountList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Savings Accounts found in the database");
        }
        return savingsAccountList;
    }

    public SavingsAccount getSavingsAccountById(Integer id) {
        Optional<SavingsAccount> foundSavingsAccount = savingsAccountRepository.findById(id);
        if (foundSavingsAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Savings Account found with that ID");
        } else {
            utils.validateLoggedUserIsAccOwner(foundSavingsAccount.get());
            addInterest(foundSavingsAccount.get());
            return foundSavingsAccount.get();
        }
    }

    public SavingsAccount saveSavingsAccount(SavingsAccountDTO dto) {
        var primaryOwner = accountHolderRepository.findById(dto.getPrimaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getPrimaryOwner()));


        var secondaryOwner = dto.getSecondaryOwner() != null
                ? accountHolderRepository.findById(dto.getSecondaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getSecondaryOwner()))
                : null;


        var savingsAccount = secondaryOwner != null
                ? SavingsAccount.fromDTO(dto, primaryOwner, secondaryOwner)
                : SavingsAccount.fromDTO(dto, primaryOwner);


        try {return savingsAccountRepository.save(savingsAccount);}
        catch(Exception e) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Savings Account");}
    }

    public void updateSavingsAccount(Integer id, SavingsAccount savingsAccount) {

    }

    public void addInterest(SavingsAccount savingsAccount){

        LocalDate today = LocalDate.now();
        Period interestAddedDate = Period.between(savingsAccount.getDateInterestAdded(), today);

        if(interestAddedDate.getYears() >= 1) {
            //add interest to balance multiplied by years passed since last interest was added, update interest added date

            log.info(Color.YELLOW_BOLD_BRIGHT+"Years passed since last interest rate added: {}"+Color.RESET
                    , interestAddedDate.getYears());

            var balance = savingsAccount.getBalance().getAmount();
            var interest = balance.multiply(savingsAccount.getInterestRate()).multiply(BigDecimal.valueOf(interestAddedDate.getYears()));

            savingsAccount.setBalance(new Money(balance.add(interest), savingsAccount.getBalance().getCurrency()));
            savingsAccount.setDateInterestAdded(today);

            log.info(Color.YELLOW_BOLD_BRIGHT+"Adding yearly interest of {} to Savings Account with ID {}"+Color.RESET
                    , interest.setScale(1, RoundingMode.HALF_UP), savingsAccount.getId());

            try {savingsAccountRepository.save(savingsAccount);}
            catch(Exception e) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Savings Account");}
        }


    }

    public void deleteSavingsAccount(Integer id) {
        Optional<SavingsAccount> foundSavingsAccount = savingsAccountRepository.findById(id);
        if (foundSavingsAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Savings Account found with that ID");
        }
        savingsAccountRepository.delete(foundSavingsAccount.get());
    }
}
