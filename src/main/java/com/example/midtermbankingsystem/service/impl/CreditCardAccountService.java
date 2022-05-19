package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.example.midtermbankingsystem.model.CreditCardAccount;
import com.example.midtermbankingsystem.model.Money;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.CreditCardAccountRepository;
import com.example.midtermbankingsystem.service.interfaces.ICreditCardAccountService;
import com.example.midtermbankingsystem.utils.Color;
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
public class CreditCardAccountService implements ICreditCardAccountService {

    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    public List<CreditCardAccount> getAllCreditCardAccounts() {
        List<CreditCardAccount> creditCardAccountList = creditCardAccountRepository.findAll();
        if (creditCardAccountList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Accounts found in the database");
        }
        return creditCardAccountList;
    }

    public CreditCardAccount getCreditCardAccountById(Integer id) {
        Optional<CreditCardAccount> foundCreditCardAccount = creditCardAccountRepository.findById(id);
        if (foundCreditCardAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        } else {
            addInterest(foundCreditCardAccount.get());
            return foundCreditCardAccount.get();
        }
    }

    public CreditCardAccount saveCreditCardAccount(CreditCardAccountDTO dto) {
        var primaryOwner = accountHolderRepository.findById(dto.getPrimaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getPrimaryOwner()));


        var secondaryOwner = dto.getSecondaryOwner() != null
                ? accountHolderRepository.findById(dto.getSecondaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getSecondaryOwner()))
                : null;


        var creditCardAccount = secondaryOwner != null
                ? CreditCardAccount.fromDTO(dto, primaryOwner, secondaryOwner)
                : CreditCardAccount.fromDTO(dto, primaryOwner);

        try {
            return creditCardAccountRepository.save(creditCardAccount);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Credit Card Account");
        }
    }

    public void updateCreditCardAccount(Integer id, CreditCardAccount creditCardAccount) {

    }

    public void addInterest(CreditCardAccount creditCardAccount) {

        LocalDate today = LocalDate.now();
        Period interestAddedDate = Period.between(creditCardAccount.getDateInterestAdded(), today);


        if (interestAddedDate.getMonths() >= 1) {
            //add interest to balance multiplied by months passed since last interest was added, update interest added date

            log.info(Color.YELLOW_BOLD_BRIGHT + "Months passed since last interest rate added: {}" + Color.RESET
                    , interestAddedDate.getMonths());

            var balance = creditCardAccount.getBalance().getAmount();
            var interest = balance.multiply(creditCardAccount.getInterestRate()).multiply(BigDecimal.valueOf(interestAddedDate.getMonths()));

            creditCardAccount.setBalance(new Money(balance.add(interest), creditCardAccount.getBalance().getCurrency()));
            creditCardAccount.setDateInterestAdded(today);

            log.info(Color.YELLOW_BOLD_BRIGHT + "Adding monthly interest of {} to Credit Card Account with ID {}" + Color.RESET
                    , interest.setScale(1, RoundingMode.HALF_UP), creditCardAccount.getId());

            try {
                creditCardAccountRepository.save(creditCardAccount);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Credit Card Account");
            }
        }

    }


    public void deleteCreditCardAccount(Integer id) {
        Optional<CreditCardAccount> foundCreditCardAccount = creditCardAccountRepository.findById(id);
        if (foundCreditCardAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        }
        creditCardAccountRepository.delete(foundCreditCardAccount.get());
    }
}
