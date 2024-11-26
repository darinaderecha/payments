package com.privat.payments.dto;

import com.privat.payments.model.Payment;
import com.privat.payments.model.Status;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ChargeDto(@NotNull(message = "chargeId is mandatory")
                        UUID chargeId,
                        @NotNull(message = "paymentId is mandatory")
                        UUID payment,
                        LocalDateTime chargeTime,
                        BigDecimal amount,
                        Status status) {
}
