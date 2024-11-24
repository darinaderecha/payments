package com.privat.payments.dto;

import com.privat.payments.model.Card;
import com.privat.payments.model.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClientCreateDto(String name,
                              Gender gender,
                              LocalDate birthdate,
                              String nationality,
                              String email,
                              String phoneNumber,
                              String passportId,
                              String TIN,
                              List<Card> cards) {

}
