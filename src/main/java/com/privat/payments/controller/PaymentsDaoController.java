package com.privat.payments.controller;


import com.privat.payments.dto.PaymentCreateDto;
import com.privat.payments.model.Payment;
import com.privat.payments.model.Card;
import com.privat.payments.repository.CardRepository;
import com.privat.payments.repository.ChargeRepository;
import com.privat.payments.repository.RegularPaymentsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/v1/payments-dao")
public class PaymentsDaoController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentsDaoController.class);

    private RegularPaymentsRepository paymentsRepository;
    private ChargeRepository chargeRepository;
    private CardRepository cardRepository;

    public PaymentsDaoController(RegularPaymentsRepository paymentsRepository,
                                     ChargeRepository chargeRepository,
                                    CardRepository cardRepository) {
        this.paymentsRepository = paymentsRepository;
        this.chargeRepository = chargeRepository;
        this.cardRepository = cardRepository;
    }

    @PostMapping
    public ResponseEntity<Payment> savePayment(@RequestBody PaymentCreateDto paymentDTO) {
        logger.info("Searching for a card with Id " + paymentDTO.card());
        Card card = cardRepository.findById(paymentDTO.card())
                .orElseThrow(() -> new EntityNotFoundException("Card not found with ID: " + paymentDTO.card()));
        logger.info("Card is found with an id " +  paymentDTO.card());
        Payment payment = new Payment();
        payment.setCard(card);
        payment.setIban(paymentDTO.IBAN());
        payment.setMfo(paymentDTO.MFO());
        payment.setZkpo(paymentDTO.ZKPO());
        payment.setReceiverName(paymentDTO.receiverName());
        payment.setAmount(paymentDTO.amount());
        payment.setWithdrawalPeriod(paymentDTO.withdrawalPeriod());
        Payment savedPayment = paymentsRepository.save(payment);
        return ResponseEntity.ok(savedPayment);
    }


}
