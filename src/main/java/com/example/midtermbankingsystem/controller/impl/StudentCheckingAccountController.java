package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.IStudentCheckingAccountController;
import com.example.midtermbankingsystem.model.StudentCheckingAccount;
import com.example.midtermbankingsystem.service.interfaces.IStudentCheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student-checking-accounts")
public class StudentCheckingAccountController implements IStudentCheckingAccountController {

    @Autowired
    private IStudentCheckingAccountService studentCheckingAccountService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentCheckingAccount saveStudentCheckingAccount(StudentCheckingAccount studentCheckingAccount) {
        return studentCheckingAccountService.saveStudentCheckingAccount(studentCheckingAccount);
    }
}
