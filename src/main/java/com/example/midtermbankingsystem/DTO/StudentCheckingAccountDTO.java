package com.example.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCheckingAccountDTO {

    private BigDecimal balance;
    private Currency currency;
    private Integer primaryOwner;
    private Integer secondaryOwner;
    private String secretKey;

    public static StudentCheckingAccountDTO fromCheckingDTO (CheckingAccountDTO dto){
        return new StudentCheckingAccountDTO(dto.getBalance(), dto.getCurrency(), dto.getPrimaryOwner()
                , dto.getSecondaryOwner(), dto.getSecretKey());
    }
}
