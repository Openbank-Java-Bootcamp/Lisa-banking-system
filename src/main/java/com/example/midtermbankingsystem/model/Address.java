package com.example.midtermbankingsystem.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String streetAddress;
    private String city;
    private String country;
    private String postalCode;
}
