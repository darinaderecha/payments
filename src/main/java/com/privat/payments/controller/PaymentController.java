package com.privat.payments.controller;

import com.privat.payments.dto.PaymentCreateDto;
import com.privat.payments.dto.PaymentDto;
import com.privat.payments.model.Payment;
import com.privat.payments.model.Card;
import com.privat.payments.repository.CardRepository;
import com.privat.payments.repository.PaymentsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/payments-dao")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private PaymentsRepository paymentsRepository;
    private CardRepository cardRepository;

    public PaymentController(PaymentsRepository paymentsRepository,
                             CardRepository cardRepository) {
        this.paymentsRepository = paymentsRepository;
        this.cardRepository = cardRepository;
    }

    @PostMapping
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentCreateDto paymentDTO) {
        logger.info("Searching for a card with Id " + paymentDTO.card());
        Card card = cardRepository.findById(paymentDTO.card())
                .orElseThrow(() -> new EntityNotFoundException("Card not found with ID: " + paymentDTO.card()));
        logger.info("Card is found with an id " + paymentDTO.card());
        Payment payment = new Payment();
        payment.setCard(card);
        payment.setIban(paymentDTO.iban());
        payment.setMfo(paymentDTO.mfo());
        payment.setZkpo(paymentDTO.zkpo());
        payment.setReceiverName(paymentDTO.receiverName());
        payment.setAmount(paymentDTO.amount());
        payment.setWithdrawalPeriod(paymentDTO.withdrawalPeriod());
        Payment savedPayment = paymentsRepository.save(payment);
        PaymentDto dto = convertToDto(savedPayment);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable UUID id,
                                                    @RequestBody PaymentCreateDto paymentDTO) {
        logger.info("Updating payment with ID: " + id);

        Payment payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        logger.info("Payment found with ID: " + id);

        Card card = cardRepository.findById(paymentDTO.card())
                .orElseThrow(() -> new EntityNotFoundException("Card not found with ID: " + paymentDTO.card()));
        logger.info("Card is found with an id " + paymentDTO.card());

        payment.setCard(card);
        payment.setIban(paymentDTO.iban());
        payment.setMfo(paymentDTO.mfo());
        payment.setZkpo(paymentDTO.zkpo());
        payment.setReceiverName(paymentDTO.receiverName());
        payment.setAmount(paymentDTO.amount());
        payment.setWithdrawalPeriod(paymentDTO.withdrawalPeriod());

        Payment updatedPayment = paymentsRepository.save(payment);
        PaymentDto dto = convertToDto(updatedPayment);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") UUID id) {
        logger.info("Deleting payment with ID: " + id);

        Payment payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        paymentsRepository.delete(payment);
        logger.info("Payment deleted with ID: " + id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> findPaymentById(@PathVariable("id") UUID id) {
        logger.info("Searching for payment with ID: " + id);

        Payment payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        logger.info("Payment found with ID: " + id);
        PaymentDto dto = convertToDto(payment);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/by-itn/{itn}")
    public ResponseEntity<?> findPaymentByITN(@PathVariable("itn") String itn) {
        logger.info("Searching for payments with ITN: " + itn);

        List<Payment> payments = paymentsRepository.findByClientITN(itn);
        if (payments.isEmpty()) {
            logger.warn("No payments found for client ITN: " + itn);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No payments found for ITN: " + itn);
        }
        logger.info("Found " + payments.size() + " payments for client ITN: " + itn);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/by-zkpo/{zkpo}")
    public ResponseEntity<?> findPaymentByZKPO(@PathVariable("zkpo") String zkpo) {
        logger.info("Searching for payments with zkpo: " + zkpo);

        List<Payment> payments = paymentsRepository.findByReceiverZKPO(zkpo);
        if (payments.isEmpty()) {
            logger.warn("No payments found for receiver zkpo: " + zkpo);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No payments found for zkpo: " + zkpo);
        }
        logger.info("Found " + payments.size() + " payments for receiver zkpo: " + zkpo);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<Payment> payments = paymentsRepository.findAll();
        List<PaymentDto> dtoPayments = new ArrayList<>();
        for (Payment p : payments) {
            PaymentDto dto = convertToDto(p);
            dtoPayments.add(dto);
        }

        return ResponseEntity.ok(dtoPayments);
    }

    private static PaymentDto convertToDto(Payment payment) {
        return new PaymentDto(payment.getPaymentId(),
                payment.getCard().getCardId(),
                payment.getIban(),
                payment.getMfo(),
                payment.getZkpo(),
                payment.getReceiverName(),
                payment.getAmount(),
                payment.getWithdrawalPeriod());
    }
}
