package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AccountHolder extends User{
    private Instant dateOfBirth;

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

    @OneToMany(mappedBy = "primaryOwner")
    private List<Account> primaryAccountList;


    @OneToMany(mappedBy = "secondaryOwner")
    private List<Account> secondaryAccountList;

    public AccountHolder(Integer id, String name, String password,Instant dateOfBirth, Address primaryAddress,
                         Address mailingAddress) {
        super(id, name, password, Role.ACCOUNT_HOLDER);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }
}
