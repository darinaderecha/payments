package com.privat.payments.dto;

import java.util.UUID;

public record CardDto(UUID cardId,
                      UUID client,
                      String cardNumber) {
}
