package com.example.midtermbankingsystem.DTO;

import com.example.midtermbankingsystem.enums.Role;
import com.example.midtermbankingsystem.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderDTO {
    @NotNull(message = "Account Holder's Name must be specified")
    private String name;

    @NotNull(message = "Account Holder's Username must be specified")
    private String username;

    @NotNull(message = "Account Holder's Password must be specified")
    private String password;

    @NotNull(message = "Account Holder's Date of Birth must be specified")
    private LocalDate dateOfBirth;

    @NotNull(message = "Account Holder's Primary Address must be specified")
    private Address primaryAddress;

    private Address mailingAddress;
}
