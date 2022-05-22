package com.ironhack.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    @NotNull(message = "Admin Name must be specified")
    private String name;

    @NotNull(message = "Admin Username must be specified")
    private String username;

    @NotNull(message = "Admin Password must be specified")
    private String password;
}
