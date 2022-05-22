package com.ironhack.midtermbankingsystem.model;

import com.ironhack.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

@AllArgsConstructor
@Data
@Entity
public class StudentCheckingAccount extends Account{

    public StudentCheckingAccount(Money balance, AccountHolder primaryOwner, String secretKey) {
        super(balance, primaryOwner, secretKey);
    }

    public static StudentCheckingAccount fromDTO(StudentCheckingAccountDTO dto, AccountHolder primary, AccountHolder secondary) {
        StudentCheckingAccount studentCheckingAccount = StudentCheckingAccount.fromDTO(dto, primary);
        studentCheckingAccount.setSecondaryOwner(secondary);
        return studentCheckingAccount;
    }


    public static StudentCheckingAccount fromDTO(StudentCheckingAccountDTO dto, AccountHolder primary) {
        return new StudentCheckingAccount(new Money(dto.getBalance(), dto.getCurrency()), primary, dto.getSecretKey());
    }
}
