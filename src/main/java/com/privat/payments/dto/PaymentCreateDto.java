package com.privat.payments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
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
                               @NotBlank(message = "Receiver name is mandatory")
                               String receiverName,
                               @NotNull(message = "Amount is mandatory")
                               BigDecimal amount,
                               @NotNull(message = "Withdrawal period is mandatory")
                               Long withdrawalPeriod) {
}
