package com.example.midtermbankingsystem.model;


import com.example.midtermbankingsystem.DTO.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Money amount;

    @ManyToOne
    @JoinColumn
    private Account payerAcc;


    @ManyToOne
    @JoinColumn
    private Account targetAcc;

    private String targetName;
    private String subject;

    @CreationTimestamp
    private Instant transferDate;

    public Transaction(Money amount, Account payerAcc, Account targetAcc, String targetName, String subject) {
        this.amount = amount;
        this.payerAcc = payerAcc;
        this.targetAcc = targetAcc;
        this.targetName = targetName;
        this.subject = subject;
    }

    public static Transaction fromDTO(TransactionDTO dto, Optional<Account> payerAcc, Optional<Account> targetAcc) {

        var payer = payerAcc.isPresent() ? payerAcc.get() : null;
        var target = targetAcc.isPresent() ? targetAcc.get() : null;

        return new Transaction(new Money(dto.getAmount(), dto.getCurrency()), payer, target, dto.getTargetName(), dto.getSubject());
    }
}
