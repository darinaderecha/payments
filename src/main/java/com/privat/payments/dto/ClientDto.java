package com.privat.payments.dto;

import com.privat.payments.model.Card;
import com.privat.payments.model.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClientDto(UUID clientId,
                        String name,
                        Gender gender,
                        LocalDate birthdate,
                        String nationality,
                        String email,
                        String phoneNumber,
                        String passportId,
                        String TIN,
                        List<Card> cards) {
}
