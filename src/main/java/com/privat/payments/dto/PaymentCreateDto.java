package com.privat.payments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.UUID;


public record PaymentCreateDto(@NotBlank(message = "Card id is mandatory")
                               UUID card,
                               @NotBlank(message = "IBAN is mandatory")
                               @Size(min = 15, max = 34)
                               String IBAN,
                               @Size(min = 5, max = 9)
                               String MFO,
                               @Size(min = 5, max = 12)
                               String ZKPO,
                               @NotBlank
                               String receiverName,
                               @NotNull
                               Double amount,
                               @NotNull
                               String withdrawalPeriod) {
}
