package com.privat.payments.service;

import com.privat.payments.model.Charge;
import com.privat.payments.repository.ChargeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChargesService {
    private ChargeRepository repository;

    public ChargesService(ChargeRepository repository) {
        this.repository = repository;
    }

    public Charge saveCharge(Charge charge) {
        return repository.save(charge);
    }

    public Charge updateCharge(UUID chargeId, Charge charge) {
        Charge existingCharge = repository.findById(chargeId)
                .orElseThrow(() -> new EntityNotFoundException("Charge not found"));

        // Update details
        //todo remake details

        return repository.save(existingCharge);
    }

    public void deleteCharge(UUID chargeId) {
        if (!repository.existsById(chargeId)) {
            throw new EntityNotFoundException("Charge not found");
        }
        repository.deleteById(chargeId);
    }

    public Charge findChargeById(UUID chargeId) {
        return repository.findById(chargeId)
                .orElseThrow(() -> new EntityNotFoundException("Charge not found"));
    }

    public List<Charge> findChargeByRegularPayment(UUID regularPaymentId) {
        List<Charge> charges = repository.findByRegularPaymentId((regularPaymentId));
        if (charges.isEmpty()) {
            throw new EntityNotFoundException("Charges not found for RegularPayment ID: " + regularPaymentId);
        }
        return charges;
    }
}
