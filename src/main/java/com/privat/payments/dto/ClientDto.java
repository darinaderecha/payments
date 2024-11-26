package com.privat.payments.dto;

import com.privat.payments.model.Card;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClientDto(UUID clientId,
                        @NotBlank(message = "Receiver name is mandatory")
                        String name,
                        @NotBlank(message = "Itn is mandatory")
                        @Size(max = 10)
                        String itn) {
}
