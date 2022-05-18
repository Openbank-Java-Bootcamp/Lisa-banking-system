package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import com.example.midtermbankingsystem.controller.interfaces.IStudentCheckingAccountController;
import com.example.midtermbankingsystem.model.StudentCheckingAccount;
import com.example.midtermbankingsystem.service.interfaces.IStudentCheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/student-checking-accounts")
public class StudentCheckingAccountController implements IStudentCheckingAccountController {

    @Autowired
    private IStudentCheckingAccountService studentCheckingAccountService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentCheckingAccount saveStudentCheckingAccount(@RequestBody @Valid StudentCheckingAccountDTO studentCheckingAccountDTO) {
        return studentCheckingAccountService.createStudentCheckingAccount(studentCheckingAccountDTO);
    }
}
