package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.enums.Status;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Account {
    @Id
    private String id;

    private Money balance;

    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    //TODO optional ??
    private AccountHolder secondaryOwner;

    private Instant creationDate;

    private Status status;

    @OneToMany(mappedBy = "payer")
    private List<Transaction> payerTransactionList;


    @OneToMany(mappedBy = "payee")
    private List<Transaction> payeeTransactionList;

}
