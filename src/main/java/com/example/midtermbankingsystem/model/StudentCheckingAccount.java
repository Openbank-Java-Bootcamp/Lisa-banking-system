package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class StudentCheckingAccount extends Account{

    private String secretKey;

    public StudentCheckingAccount(Money balance, AccountHolder primaryOwner, String secretKey) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
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
