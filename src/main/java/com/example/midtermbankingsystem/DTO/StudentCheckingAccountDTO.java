package com.example.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCheckingAccountDTO {
    @NotNull(message = "Account's Balance must be specified")
    private BigDecimal balance;

    @NotNull(message = "Account's Currency must be specified")
    private Currency currency;

    @NotNull(message = "Account's Primary Owner must be specified")
    private Integer primaryOwner;

    private Integer secondaryOwner;

    @NotNull(message = "Account's Secret Key must be specified")
    private String secretKey;

    public static StudentCheckingAccountDTO fromCheckingDTO (CheckingAccountDTO dto){
        return new StudentCheckingAccountDTO(dto.getBalance(), dto.getCurrency(), dto.getPrimaryOwner()
                , dto.getSecondaryOwner(), dto.getSecretKey());
    }
}
