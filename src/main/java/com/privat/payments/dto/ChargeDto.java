package com.privat.payments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privat.payments.model.Status;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ChargeDto(@NotNull(message = "chargeId is mandatory")
                        UUID chargeId,
                        @NotNull(message = "paymentId is mandatory")
                        UUID payment,
                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                        LocalDateTime chargeTime,
                        @NotNull(message = "amount is mandatory")
                        BigDecimal amount,
                        Status status) {
}
