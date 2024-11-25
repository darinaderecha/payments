package com.privat.payments.dto;

import com.privat.payments.model.Payment;
import com.privat.payments.model.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChargeDto(UUID chargeId,
                        Payment payment,
                        LocalDateTime chargeTime,
                        Double amount,
                        Status status) {
}
