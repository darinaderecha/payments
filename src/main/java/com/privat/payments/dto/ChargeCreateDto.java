package com.privat.payments.dto;

import com.privat.payments.model.Payment;
import com.privat.payments.model.Status;

import java.time.LocalDateTime;

public record ChargeCreateDto(Payment payment,
                              LocalDateTime chargeTime,
                              Double amount,
                              Status status) {
}
