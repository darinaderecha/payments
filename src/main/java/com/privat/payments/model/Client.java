package com.privat.payments.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;

    private String name;

    private String tin;

    @OneToMany(mappedBy = "client")
    private List<Card> cards;

    public Client() {
    }

    public Client(UUID clientId,
                  String name,
                  String tin) {
        this.clientId = clientId;
        this.name = name;
        this.tin = tin;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}