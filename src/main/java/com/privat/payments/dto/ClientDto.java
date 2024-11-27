package com.privat.payments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ClientDto(UUID clientId,
                        @NotBlank(message = "Receiver name is mandatory")
                        String name,
                        @NotBlank(message = "Itn is mandatory")
                        @Size(min = 8, max = 10, message = "input size should be more than 7 and less than 11 ")
                        String itn) {
}
