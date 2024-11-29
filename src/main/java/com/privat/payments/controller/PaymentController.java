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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/payments-dao")
public class PaymentController {
    private final PaymentsRepository paymentsRepository;
    private final CardRepository cardRepository;

    public PaymentController(PaymentsRepository paymentsRepository,
                             CardRepository cardRepository) {
        this.paymentsRepository = paymentsRepository;
        this.cardRepository = cardRepository;
    }

    @PostMapping
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentCreateDto paymentDTO) {
        Card card = cardRepository.findById(paymentDTO.card())
                .orElseThrow(() -> new EntityNotFoundException("Card not found with ID: " + paymentDTO.card()));
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

        Payment payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));

        Card card = cardRepository.findById(paymentDTO.card())
                .orElseThrow(() -> new EntityNotFoundException("Card not found with ID: " + paymentDTO.card()));
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
        Payment payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        paymentsRepository.delete(payment);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> findPaymentById(@PathVariable("id") UUID id) {
        Payment payment = paymentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        PaymentDto dto = convertToDto(payment);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/by-itn/{itn}")
    public ResponseEntity<List<PaymentDto>> findPaymentByITN(@PathVariable("itn") String itn,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentsPage = paymentsRepository.findByClientITN(itn, pageable);
        if (paymentsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<PaymentDto> dtoPayments = paymentsPage.getContent().stream()
                .map(PaymentController::convertToDto)
                .toList();

        return ResponseEntity.ok(dtoPayments);
    }

    @GetMapping("/by-zkpo/{zkpo}")
    public ResponseEntity<List<PaymentDto>> findPaymentByZKPO(@PathVariable("zkpo") String zkpo,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentsPage = paymentsRepository.findByReceiverZKPO(zkpo, pageable);
        if (paymentsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<PaymentDto> dtoPayments = paymentsPage.getContent().stream()
                .map(PaymentController::convertToDto)
                .toList();
      return  ResponseEntity.ok(dtoPayments);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDto>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentsPage = paymentsRepository.findAll(pageable);
        if (paymentsPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<PaymentDto> dtoPayments = paymentsPage.getContent().stream()
                .map(PaymentController::convertToDto)
                .toList();

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
