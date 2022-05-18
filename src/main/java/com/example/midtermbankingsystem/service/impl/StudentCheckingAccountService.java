package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import com.example.midtermbankingsystem.model.StudentCheckingAccount;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.StudentCheckingAccountRepository;
import com.example.midtermbankingsystem.service.interfaces.IStudentCheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCheckingAccountService implements IStudentCheckingAccountService {

    @Autowired
    private StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    public List<StudentCheckingAccount> getAllStudentCheckingAccounts() {
        List<StudentCheckingAccount> studentCheckingAccountList = studentCheckingAccountRepository.findAll();
        if (studentCheckingAccountList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Student Checking Accounts found in the database");
        }
        return studentCheckingAccountList;
    }

    public StudentCheckingAccount getStudentCheckingAccountById(String id) {
        Optional<StudentCheckingAccount> foundStudentCheckingAccount = studentCheckingAccountRepository.findById(id);
        if (foundStudentCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Student Checking Account found with that ID");
        } else {
            return foundStudentCheckingAccount.get();
        }
    }

    public StudentCheckingAccount createStudentCheckingAccount(StudentCheckingAccountDTO dto) {
        var primaryOwner = accountHolderRepository.findById(dto.getPrimaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getPrimaryOwner()));


        var secondaryOwner = dto.getSecondaryOwner() != null
                ? accountHolderRepository.findById(dto.getSecondaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getSecondaryOwner()))
                : null;


        var studentCheckingAccount = secondaryOwner != null
                ? StudentCheckingAccount.fromDTO(dto, primaryOwner, secondaryOwner)
                : StudentCheckingAccount.fromDTO(dto, primaryOwner);

        try {return studentCheckingAccountRepository.save(studentCheckingAccount);}
        catch(Exception e) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Student Checking Account");}
    }

    public void updateStudentCheckingAccount(String id, StudentCheckingAccount studentCheckingAccount) {

    }

    public void deleteStudentCheckingAccount(String id) {
        Optional<StudentCheckingAccount> foundStudentCheckingAccount = studentCheckingAccountRepository.findById(id);
        if (foundStudentCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Student Checking Account found with that ID");
        }
        studentCheckingAccountRepository.delete(foundStudentCheckingAccount.get());
    }
}
