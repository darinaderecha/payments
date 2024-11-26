package com.privat.payments.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;

    private String name;

    private String itn;

    @OneToMany(mappedBy = "client")
    private List<Card> cards;

    public Client() {
    }

    public Client(UUID clientId,
                  String name,
                  String itn) {
        this.clientId = clientId;
        this.name = name;
        this.itn = itn;
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

    public String getItn() {
        return itn;
    }

    public void setItn(String tin) {
        this.itn = tin;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}