package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import com.example.midtermbankingsystem.model.StudentCheckingAccount;

public interface IStudentCheckingAccountController {
    StudentCheckingAccount saveStudentCheckingAccount(StudentCheckingAccountDTO studentCheckingAccountDTO);

}
