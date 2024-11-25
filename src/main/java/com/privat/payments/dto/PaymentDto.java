package com.privat.payments.dto;

import com.privat.payments.model.Card;

import java.sql.Timestamp;
import java.util.UUID;

public record PaymentDto(UUID id,
                         Card card,
                         String IBAN,
                         String MFO,
                         String ZKPO,
                         String receiverName,
                         Boolean isActive,
                         Double amount,
                         Timestamp withdrawalPeriod) {
}
