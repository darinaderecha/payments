package com.privat.payments.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name= "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cardId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private String cardNumber;

    private String cvv;

    private LocalDate expirationDate;

    private String iban;

    private BigDecimal balance;

    @OneToMany(mappedBy = "card")
    private List<Payment> payments;


    public Card() {
    }

    public Card(UUID cardId,
                Client client,
                String cardNumber,
                String cvv,
                LocalDate expirationDate,
                String iban,
                BigDecimal balance) {
        this.cardId = cardId;
        this.client = client;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.iban = iban;
        this.balance = balance;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
