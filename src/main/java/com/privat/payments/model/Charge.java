package com.privat.payments.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="charge")
public class Charge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID chargeId;

    @ManyToOne
    @JoinColumn(name = "regular_payment_id")
    private Payment payment;

    private LocalDateTime chargeTime;

    private Double amount;

    private Status status;

    public Charge() {
    }

    public Charge(UUID chargeId,
                  Payment payment,
                  LocalDateTime chargeTime,
                  Double amount,
                  Status status) {
        this.chargeId = chargeId;
        this.payment = payment;
        this.chargeTime = chargeTime;
        this.amount = amount;
        this.status = status;
    }

    public UUID getChargeId() {
        return chargeId;
    }

    public void setChargeId(UUID chargeId) {
        this.chargeId = chargeId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public LocalDateTime getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(LocalDateTime chargeTime) {
        this.chargeTime = chargeTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
