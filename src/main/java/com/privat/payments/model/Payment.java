package com.privat.payments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "regular_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID regularPaymentId;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private String iban;

    private String mfo;

    private String zkpo;

    private String receiverName;

    private BigDecimal amount;

    private Long withdrawalPeriod;

    public Payment() {
    }

    public Payment(UUID regularPaymentId,
                   Card card,
                   String iban,
                   String mfo,
                   String zkpo,
                   String receiverName,
                   BigDecimal amount,
                   Long withdrawalPeriod) {
        this.regularPaymentId = regularPaymentId;
        this.card = card;
        this.iban = iban;
        this.mfo = mfo;
        this.zkpo = zkpo;
        this.receiverName = receiverName;
        this.amount = amount;
        this.withdrawalPeriod = withdrawalPeriod;

    }

    public UUID getRegularPaymentId() {
        return regularPaymentId;
    }

    public void setRegularPaymentId(UUID regularPaymentId) {
        this.regularPaymentId = regularPaymentId;
    }
    @JsonIgnore
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getMfo() {
        return mfo;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }

    public String getZkpo() {
        return zkpo;
    }

    public void setZkpo(String zkpo) {
        this.zkpo = zkpo;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getWithdrawalPeriod() {
        return withdrawalPeriod;
    }

    public void setWithdrawalPeriod(Long withdrawalPeriod) {
        this.withdrawalPeriod = withdrawalPeriod;
    }
}