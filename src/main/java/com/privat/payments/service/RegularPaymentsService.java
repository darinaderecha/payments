package com.privat.payments.service;

import com.privat.payments.dto.PaymentCreateDto;
import com.privat.payments.model.Card;
import com.privat.payments.model.Charge;
import com.privat.payments.model.Payment;
import com.privat.payments.repository.CardRepository;
import com.privat.payments.repository.ChargeRepository;
import com.privat.payments.repository.RegularPaymentsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegularPaymentsService {
    private RegularPaymentsRepository paymentsRepository;
    private CardRepository cardRepository;
    private ChargeRepository chargeRepository;

    public RegularPaymentsService(RegularPaymentsRepository paymentsRepository,
                                  CardRepository cardRepository,
                                  ChargeRepository chargeRepository) {
        this.paymentsRepository = paymentsRepository;
        this.cardRepository = cardRepository;
        this.chargeRepository = chargeRepository;

    }

    public Payment registerPaymentByCardId(PaymentCreateDto paymentDto) {
        Card card = cardRepository.findById(paymentDto.card())
                .orElseThrow(() -> new EntityNotFoundException("Card not found with ID: " + paymentDto.card()));
        Payment payment = new Payment();
        payment.setCard(card);
        payment.setIban(paymentDto.IBAN());
        payment.setMfo(paymentDto.MFO());
        payment.setZkpo(paymentDto.ZKPO());
        payment.setReceiverName(paymentDto.receiverName());
        payment.setAmount(paymentDto.amount());
        return paymentsRepository.save(payment);
    }

    //updatepayment
    public Payment updatePayment(UUID paymentId, Payment updatedDetails) {
        // Find the existing payment
        Payment existingPayment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        // Update details
        //todo remake details

        // Save and return updated entity
        return paymentsRepository.save(existingPayment);
    }

    //deletepayment
    public void deletePayment(UUID paymentId) {
        if (!paymentsRepository.existsById(paymentId)) {
            throw new EntityNotFoundException("Payment not found");
        }
        paymentsRepository.deleteById(paymentId); // Deletes by ID
    }

    //get payment by id regular
    private Payment findPaymentById(UUID paymentId) {
        return paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
    }

    public List<Payment> findByClientTIN(String tin) {
        List<Payment> payments = paymentsRepository.findByClientTIN(tin);
        if (payments.isEmpty()) {
            throw new EntityNotFoundException("Regular payments not found for client TIN: " + tin);
        }
        return payments;
    }

    public List<Payment> findByReceiverZKPO(String zkpo) {
        List<Payment> payments = paymentsRepository.findReceiverZKPO(zkpo);
        if (payments.isEmpty()) {
            throw new EntityNotFoundException("Regular payments not found for receiver ZKPO: " + zkpo);
        }
        return payments;
    }

    public Charge saveCharge(Charge charge) {
        return chargeRepository.save(charge);
    }

    public Charge updateCharge(UUID chargeId, Charge charge) {
        Charge existingCharge = chargeRepository.findById(chargeId)
                .orElseThrow(() -> new EntityNotFoundException("Charge not found"));

        // Update details
        //todo remake details

        return chargeRepository.save(existingCharge);
    }

    public void deleteCharge(UUID chargeId) {
        if (!chargeRepository.existsById(chargeId)) {
            throw new EntityNotFoundException("Charge not found");
        }
        chargeRepository.deleteById(chargeId);
    }

    public Charge findChargeById(UUID chargeId) {
        return chargeRepository.findById(chargeId)
                .orElseThrow(() -> new EntityNotFoundException("Charge not found"));
    }

    public List<Charge> findChargeByRegularPayment(UUID regularPaymentId) {
        List<Charge> charges = chargeRepository.findByRegularPaymentId((regularPaymentId));
        if (charges.isEmpty()) {
            throw new EntityNotFoundException("Charges not found for RegularPayment ID: " + regularPaymentId);
        }
        return charges;
    }

    public Charge makeCharge(UUID paymentId) {

        //1.validate charge: find ChargeBNyRegularPayment
        //find last charge
        //if charge was withdrowed comparing last time and now
        //if its first checj cronos and now
        //if it ccurrent check last and now nd check if it doesn't exeeds term
        //if term is more often then 4 hours - count how many withdrals should be in between
        //test if withrawal each 4 hours
        return null;
    }

    public Boolean checkIfNeedToWithdraw(){
        return null;
    }
    public Charge deleteCharge() {
        //returmn money on bank account
    return null;
    }

}
