package com.ironhack.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyDTO {
    @NotNull(message = "Third Party's Name must be specified")
    private String name;

    @NotNull(message = "Third Party's Username must be specified")
    private String username;

    private String password;

    @NotNull(message = "Third Party's Hashed Key must be specified")
    private String hashedKey;

    @NotNull(message = "Third Party's Account ID must be specified")
    private Integer thirdPartyAccount;
}
