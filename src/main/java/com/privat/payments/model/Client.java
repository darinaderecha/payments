package com.privat.payments.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="client")
public class Client {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "passport_id")
    private String passportId;

    @Column(name = "tin")
    private String TIN;

    @Column(name = "card_id")
    @OneToMany(mappedBy = "client")
    private List<Card> cards;

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}