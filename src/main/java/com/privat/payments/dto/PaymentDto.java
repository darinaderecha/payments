package com.privat.payments.dto;

import com.privat.payments.model.Card;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentDto(@NotNull(message = "Regular payment id is mandatory")
                         UUID id,
                         @NotBlank(message = "Card id is mandatory")
                         UUID card,
                         @NotBlank(message = "IBAN is mandatory")
                         @Size(min = 15, max = 34, message = "size should be more than 14 and less than 3 than")
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
                         Long withdrawalPeriod)  {
}
