package com.example.midtermbankingsystem.DTO;

import com.example.midtermbankingsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusDTO {
    @NotNull(message = "Account status must be specified")
    @Enumerated
    private Status status;
}
