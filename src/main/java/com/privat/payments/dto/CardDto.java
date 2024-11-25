package com.privat.payments.dto;

import com.privat.payments.model.Client;

import java.time.LocalDate;
import java.util.UUID;

public record CardDto(UUID cardId,
                      Client client,
                      String cardNumber,
                      String cvv,
                      LocalDate expirationDate,
                      String IBAN,
                      Boolean isActive,
                      Double balance) {
}
