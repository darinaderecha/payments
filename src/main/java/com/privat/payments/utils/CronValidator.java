package com.privat.payments.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.scheduling.support.CronExpression;

public class CronValidator implements ConstraintValidator<ValidCron, String> {

    @Override
    public boolean isValid(String cronExpression, ConstraintValidatorContext context) {
        if (cronExpression == null || cronExpression.isBlank()) {
            return false;
        }
        try {
            CronExpression.parse(cronExpression);
           return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

