package com.privat.payments.dto;

import com.privat.payments.model.Payment;
import com.privat.payments.model.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChargeCreateDto(@NotNull(message = "paymentId is mandatory")
                              UUID payment,
                              LocalDateTime chargeTime,
                              Double amount,
                              Status status) {
}
