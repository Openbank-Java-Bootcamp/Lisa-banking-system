package com.ironhack.midtermbankingsystem.service.interfaces;

import com.ironhack.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import com.ironhack.midtermbankingsystem.model.StudentCheckingAccount;

import java.util.List;

public interface IStudentCheckingAccountService {
    List<StudentCheckingAccount> getAllStudentCheckingAccounts();
    StudentCheckingAccount getStudentCheckingAccountById(Integer id);
    StudentCheckingAccount createStudentCheckingAccount(StudentCheckingAccountDTO studentCheckingAccountDTO);
    void updateStudentCheckingAccount(Integer id, StudentCheckingAccount studentCheckingAccount);
    void deleteStudentCheckingAccount(Integer id);
}
