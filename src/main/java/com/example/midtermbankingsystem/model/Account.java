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

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    })
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

    @OneToMany(mappedBy = "payerAcc")
    @JsonIgnore
    private List<Transaction> payerTransactionList;


    @OneToMany(mappedBy = "targetAcc")
    @JsonIgnore
    private List<Transaction> targetTransactionList;

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = Status.ACTIVE;
    }

    public Account(Money balance, AccountHolder primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.status = Status.ACTIVE;
    }


}
