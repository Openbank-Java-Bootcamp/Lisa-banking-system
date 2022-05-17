package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Account {
    @Id
    private String id;

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
    private List<Transaction> payerTransactionList;


    @OneToMany(mappedBy = "payee")
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
        this.setId("a1");
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = Status.ACTIVE;
    }

    public Account(Money balance, AccountHolder primaryOwner) {
//        this.setId(UUID.randomUUID().toString());
        this.setId("a2");
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.creationDate = creationDate;
        this.status = Status.ACTIVE;
    }


}
