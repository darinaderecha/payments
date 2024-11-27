package com.privat.payments.controller;

import com.privat.payments.dto.ChargeCreateDto;
import com.privat.payments.dto.ChargeDto;
import com.privat.payments.model.Charge;
import com.privat.payments.model.Payment;
import com.privat.payments.repository.ChargeRepository;
import com.privat.payments.repository.PaymentsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/charges-dao")
public class ChargeController {

        private final ChargeRepository chargeRepository;
        private final PaymentsRepository paymentsRepository;

        public ChargeController(ChargeRepository chargeRepository, PaymentsRepository paymentsRepository) {
            this.chargeRepository = chargeRepository;
            this.paymentsRepository = paymentsRepository;
        }

        @PostMapping("/")
        public ResponseEntity<ChargeDto> createCharge(@RequestBody ChargeCreateDto chargeCreateDto) {
            Payment payment = paymentsRepository.findById(chargeCreateDto.payment())
                    .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + chargeCreateDto.payment()));

            Charge charge = new Charge();
            charge.setPayment(payment);
            charge.setChargeTime(chargeCreateDto.chargeTime() != null ? chargeCreateDto.chargeTime() : LocalDateTime.now());
            charge.setAmount(chargeCreateDto.amount());
            charge.setStatus(chargeCreateDto.status());

            Charge savedCharge = chargeRepository.save(charge);
            ChargeDto chargeDto = new ChargeDto(savedCharge.getChargeId(), savedCharge.getPayment().getPaymentId(),
                    savedCharge.getChargeTime(),
                    savedCharge.getAmount(), savedCharge.getStatus());
            return ResponseEntity.ok(chargeDto);
        }

        @PutMapping("/{chargeId}")
        public ResponseEntity<ChargeDto> updateCharge(@PathVariable ("chargeId")UUID chargeId, @RequestBody ChargeDto dto) {
            Charge charge = chargeRepository.findById(chargeId)
                    .orElseThrow(() -> new EntityNotFoundException("Charge not found with ID: " + chargeId));

            if (!charge.getPayment().getPaymentId().equals(dto.payment())) {
                Payment payment = paymentsRepository.findById(dto.payment())
                        .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + dto.payment()));
                charge.setPayment(payment);
            }

            charge.setChargeTime(dto.chargeTime() != null ? dto.chargeTime() : charge.getChargeTime());
            charge.setAmount(dto.amount());
            charge.setStatus(dto.status());

            Charge updatedCharge = chargeRepository.save(charge);
            ChargeDto chargeDto = new ChargeDto(updatedCharge.getChargeId(), updatedCharge.getPayment().getPaymentId(),
                    updatedCharge.getChargeTime(),
                    updatedCharge.getAmount(), updatedCharge.getStatus());
            return ResponseEntity.ok(chargeDto);
        }

        @DeleteMapping("/{chargeId}")
        public ResponseEntity<Void> deleteCharge(@PathVariable("chargeId") UUID chargeId) {
            if (!chargeRepository.existsById(chargeId)) {
                throw new EntityNotFoundException("Charge not found with ID: " + chargeId);
            }
            chargeRepository.deleteById(chargeId);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/{chargeId}")
        public ResponseEntity<ChargeDto> findChargeById(@PathVariable("chargeId") UUID chargeId) {
            Charge charge = chargeRepository.findById(chargeId)
                    .orElseThrow(() -> new EntityNotFoundException("Charge not found with ID: " + chargeId));
            ChargeDto dto = new ChargeDto(charge.getChargeId(), charge.getPayment().getPaymentId(),
                    charge.getChargeTime(),
                    charge.getAmount(), charge.getStatus());
            return ResponseEntity.ok(dto);
        }

        @GetMapping("/payment/{paymentId}")
        public ResponseEntity<List<ChargeDto>> findChargesByPaymentId(@PathVariable("paymentId") UUID paymentId) {
            List<Charge> charges = chargeRepository.findByRegularPaymentId(paymentId);
            if (charges.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Collections.emptyList());
            }
            List<ChargeDto> chargeDtos = new ArrayList<>();
            for (Charge charge : charges){
                ChargeDto dto = new ChargeDto(charge.getChargeId(), charge.getPayment().getPaymentId(), charge.getChargeTime(),
                        charge.getAmount(), charge.getStatus());
                chargeDtos.add(dto);
            }
            chargeDtos.sort((dto1, dto2) -> dto2.chargeTime().compareTo(dto1.chargeTime()));

            return ResponseEntity.ok(chargeDtos);
        }
    }
