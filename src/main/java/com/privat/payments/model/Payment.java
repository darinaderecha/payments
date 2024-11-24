package com.privat.payments.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "regular_payment")
public class Payment {

    @Id
    @Column(name = "regular_payment_id")
    private UUID id;

    @Column(name = "card_id")
    @ManyToMany
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "iban")
    private String IBAN;

    @Column(name = "mfo")
    private String MFO;

    @Column(name = "zkpo")
    private String ZKPO;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "amount")
    private Double amount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getMFO() {
        return MFO;
    }

    public void setMFO(String MFO) {
        this.MFO = MFO;
    }

    public String getZKPO() {
        return ZKPO;
    }

    public void setZKPO(String ZKPO) {
        this.ZKPO = ZKPO;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}