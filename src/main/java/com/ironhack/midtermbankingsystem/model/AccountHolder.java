package com.ironhack.midtermbankingsystem.model;

import com.ironhack.midtermbankingsystem.DTO.AccountHolderDTO;
import com.ironhack.midtermbankingsystem.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AccountHolder extends User {

    private LocalDate dateOfBirth;

    //private final Role role = Role.ACCOUNT_HOLDER;

    @Embedded
    private Address primaryAddress;


    @AttributeOverrides({
            @AttributeOverride(name = "streetAddress", column = @Column(name = "mailing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postal"))
    })
    @Embedded
    private Address mailingAddress;

//    @OneToMany(mappedBy = "primaryOwner", cascade = {CascadeType.PERSIST})
    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> primaryAccountList = new ArrayList<>();


    @OneToMany(mappedBy = "secondaryOwner")
    @JsonIgnore
    private List<Account> secondaryAccountList;

    public AccountHolder(String name, String password, String username, LocalDate dateOfBirth, Address primaryAddress,
                         Address mailingAddress) {
        super(name, password, Role.ACCOUNT_HOLDER, username);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public static AccountHolder fromDTO(AccountHolderDTO dto) {
        return new AccountHolder(dto.getName(), dto.getPassword(), dto.getUsername(), dto.getDateOfBirth(), dto.getPrimaryAddress(),
                dto.getMailingAddress());
    }
}
