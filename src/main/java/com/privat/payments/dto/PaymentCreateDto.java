package com.privat.payments.dto;

import com.privat.payments.model.Card;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

import java.util.UUID;

public record PaymentCreateDto(Card card,
                               String IBAN,
                               String MFO,
                               String ZKPO,
                               String receiverName,
                               Boolean isActive,
                               Double amount) {
}
