package com.privat.payments.model;

import jakarta.persistence.*;

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

    @OneToMany(mappedBy = "card")
    private List<Payment> payments;


    public Card() {
    }

    public Card(UUID cardId,
                Client client,
                String cardNumber) {
        this.cardId = cardId;
        this.client = client;
        this.cardNumber = cardNumber;
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


}
