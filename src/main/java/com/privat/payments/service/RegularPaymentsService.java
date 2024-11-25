package com.privat.payments.service;

import com.privat.payments.dto.PaymentCreateDto;
import com.privat.payments.model.Card;
import com.privat.payments.model.Payment;
import com.privat.payments.repository.CardRepository;
import com.privat.payments.repository.RegularPaymentsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegularPaymentsService {
    private RegularPaymentsRepository paymentsRepository;
    private CardRepository cardRepository;

    public RegularPaymentsService(RegularPaymentsRepository paymentsRepository,
                                  CardRepository cardRepository) {
        this.paymentsRepository = paymentsRepository;
        this.cardRepository = cardRepository;
    }

    public Payment registerPaymentByCardId(PaymentCreateDto paymentDto) {
        Card card = cardRepository.findById(paymentDto.card())
                .orElseThrow(() -> new EntityNotFoundException("Card not found with ID: " + paymentDto.card()));
        return savePayment(paymentDto, card);
    }

    public Payment registerPaymentByIBAN(PaymentCreateDto paymentDto, String iban){
        Card card = cardRepository.findByIBAN(iban)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with IBAN: " + iban ));
        return savePayment(paymentDto, card);
    }

    private Payment savePayment(PaymentCreateDto paymentDto, Card card) {
        Payment payment = new Payment();
        payment.setCard(card);
        payment.setIban(paymentDto.IBAN());
        payment.setMfo(paymentDto.MFO());
        payment.setZkpo(paymentDto.ZKPO());
        payment.setReceiverName(paymentDto.receiverName());
        payment.setActive(paymentDto.isActive());
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
    public Payment findPaymentById(UUID paymentId) {
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

}
