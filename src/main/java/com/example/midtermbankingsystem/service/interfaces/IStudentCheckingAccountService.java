package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.model.StudentCheckingAccount;

import java.util.List;

public interface IStudentCheckingAccountService {
    List<StudentCheckingAccount> getAllStudentCheckingAccounts();
    StudentCheckingAccount getStudentCheckingAccountById(String id);
    StudentCheckingAccount saveStudentCheckingAccount(StudentCheckingAccount studentCheckingAccount);
    void updateStudentCheckingAccount(String id, StudentCheckingAccount studentCheckingAccount);
    void deleteStudentCheckingAccount(String id);
}
