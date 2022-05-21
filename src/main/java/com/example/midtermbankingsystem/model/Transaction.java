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
    private Integer payerThirdPartyAcc;

    @ManyToOne
    @JoinColumn
    private Account targetAcc;
    private Integer targetThirdPartyAcc;

    private String targetName;
    private String subject;
    private String secretKey;

    @CreationTimestamp
    private Instant transferDate;

    public Transaction(Money amount, Account payerAcc, Account targetAcc, String targetName, String subject
            , String secretKey, Integer payerThirdPartyAcc, Integer targetThirdPartyAcc) {
        this.amount = amount;
        this.payerAcc = payerAcc;
        this.targetAcc = targetAcc;
        this.targetName = targetName;
        this.subject = subject;
        this.secretKey = secretKey;
        this.payerThirdPartyAcc = payerThirdPartyAcc;
        this.targetThirdPartyAcc = targetThirdPartyAcc;
    }


    public static Transaction fromDTO(TransactionDTO dto, Optional<Account> payerAcc, Optional<Account> targetAcc) {

        var payer = payerAcc==null ? null : payerAcc.get();
        var target = targetAcc==null ? null : targetAcc.get();

        Integer thirdPartyAcc = dto.getThirdPartyAccount();
        Integer payerThirdPartyAcc = payer == null ? thirdPartyAcc : null;
        Integer targetThirdPartyAcc = target == null ? thirdPartyAcc : null;

        return new Transaction(new Money(dto.getAmount(), dto.getCurrency()), payer, target, dto.getTargetName()
                , dto.getSubject(), dto.getSecretKey(), payerThirdPartyAcc, targetThirdPartyAcc);
    }
}
