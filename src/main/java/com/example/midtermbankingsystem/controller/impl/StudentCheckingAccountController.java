package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.IStudentCheckingAccountController;
import com.example.midtermbankingsystem.service.interfaces.IStudentCheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StudentCheckingAccountController implements IStudentCheckingAccountController {

    @Autowired
    private IStudentCheckingAccountService studentCheckingAccountService;
}
