package com.example.midtermbankingsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Money amount;

    @ManyToOne
    @JoinColumn
    private Account payer;

    @ManyToOne
    @JoinColumn
    private Account payee;

    private Instant transferDate;
}
