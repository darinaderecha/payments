package com.privat.payments.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name= "card")
public class Card {

    @Id
    @Column(name = "card_id")
    private UUID id;

    @Column(name ="client_id")
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name ="card_number")
    private String cardNumber;

    @Column(name ="cvv")
    private String cvv;

    @Column(name ="expiration_date")
    private LocalDate expirationDate;

    @Column(name ="iban")
    private String IBAN;

    @Column(name ="is_active")
    private Boolean isActive;

    @Column(name ="is_credit_card")
    private Boolean isCreditCard;

    @Column(name ="balance")
    private Double balance;

    @Column(name ="credit_limit")
    private Double creditLimit;

    @Column(name ="brand")
    private CardBrand brand;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getCreditCard() {
        return isCreditCard;
    }

    public void setCreditCard(Boolean creditCard) {
        isCreditCard = creditCard;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public CardBrand getBrand() {
        return brand;
    }

    public void setBrand(CardBrand brand) {
        this.brand = brand;
    }
}
