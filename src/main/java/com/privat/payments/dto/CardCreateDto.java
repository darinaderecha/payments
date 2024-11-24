package com.privat.payments.dto;

import com.privat.payments.model.CardBrand;
import com.privat.payments.model.Client;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.UUID;

public record CardCreateDto(Client client,
                            String cardNumber,
                            String cvv,
                            LocalDate expirationDate,
                            String IBAN,
                            Boolean isActive,
                            Boolean isCreditCard,
                            Double balance,
                            Double creditLimit,
                            CardBrand brand) {

}
