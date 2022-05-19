package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import com.example.midtermbankingsystem.model.StudentCheckingAccount;

import java.util.List;

public interface IStudentCheckingAccountService {
    List<StudentCheckingAccount> getAllStudentCheckingAccounts();
    StudentCheckingAccount getStudentCheckingAccountById(Integer id);
    StudentCheckingAccount createStudentCheckingAccount(StudentCheckingAccountDTO studentCheckingAccountDTO);
    void updateStudentCheckingAccount(Integer id, StudentCheckingAccount studentCheckingAccount);
    void deleteStudentCheckingAccount(Integer id);
}
