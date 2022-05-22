package com.ironhack.midtermbankingsystem.controller.impl;

import com.ironhack.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.ironhack.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import com.ironhack.midtermbankingsystem.controller.interfaces.ICheckingAccountController;
import com.ironhack.midtermbankingsystem.model.Account;
import com.ironhack.midtermbankingsystem.repository.AccountHolderRepository;
import com.ironhack.midtermbankingsystem.service.interfaces.ICheckingAccountService;
import com.ironhack.midtermbankingsystem.service.interfaces.IStudentCheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/api/checking-accounts")
public class CheckingAccountController implements ICheckingAccountController {

    @Autowired
    private ICheckingAccountService checkingAccountService;

    @Autowired
    private IStudentCheckingAccountService studentCheckingAccountService;

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Account saveCheckingAccount(@RequestBody @Valid CheckingAccountDTO dto) {

        var primaryOwner = accountHolderRepository.findById(dto.getPrimaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getPrimaryOwner()));

        LocalDate today = LocalDate.now();
        Period period = Period.between(primaryOwner.getDateOfBirth(), today);

        return period.getYears() < 24
                ? studentCheckingAccountService.createStudentCheckingAccount(StudentCheckingAccountDTO.fromCheckingDTO(dto))
                : checkingAccountService.createCheckingAccount(dto);
    }
}
