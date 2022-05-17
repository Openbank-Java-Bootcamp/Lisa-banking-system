package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @AttributeOverride(name = "amount", column = @Column(name = "balance"))
    @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    @Embedded
    private Money balance;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    //TODO optional ??
    private AccountHolder secondaryOwner;

    @CreationTimestamp
    private Instant creationDate;

//    @UpdateTimestamp
//    private Instant lastUpdate;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "payer")
    @JsonIgnore
    private List<Transaction> payerTransactionList;


    @OneToMany(mappedBy = "payee")
    @JsonIgnore
    private List<Transaction> payeeTransactionList;


    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Instant creationDate,
                   Status status, List<Transaction> payerTransactionList, List<Transaction> payeeTransactionList) {
//        this.setId(UUID.randomUUID().toString());
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.creationDate = creationDate;
        this.status = status;
        this.payerTransactionList = payerTransactionList;
        this.payeeTransactionList = payeeTransactionList;
    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
//        this.setId(UUID.randomUUID().toString());

        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = Status.ACTIVE;
    }

    public Account(Money balance, AccountHolder primaryOwner) {
//        this.setId(UUID.randomUUID().toString());

        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.creationDate = creationDate;
        this.status = Status.ACTIVE;
    }


}
