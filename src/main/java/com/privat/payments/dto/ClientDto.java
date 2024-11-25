package com.privat.payments.dto;

import com.privat.payments.model.Card;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClientDto(UUID clientId,
                        String name,
                        String TIN) {
}
