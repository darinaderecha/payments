package com.privat.payments.controller;

import com.privat.payments.dto.PaymentCreateDto;
import com.privat.payments.model.Payment;
import com.privat.payments.service.RegularPaymentsService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/regular-payments")
public class RegularPaymentsController {

    private RegularPaymentsService paymentsService;

    public RegularPaymentsController(RegularPaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

  //register payment  by cardId
    @PostMapping("/payment")
   public ResponseEntity<Payment> registerPaymentByCardId( @RequestBody PaymentCreateDto paymentDto) {
    Payment payment = paymentsService.registerPaymentByCardId(paymentDto);
    return ResponseEntity.ok(payment);
}

    //register payment by IBAN
    @PostMapping("/payment/{iban}")
    public ResponseEntity<Payment> registerPaymentByIBAN( @RequestBody PaymentCreateDto paymentDto,  @PathVariable("iban") String IBAN) {
        Payment payment = paymentsService.registerPaymentByIBAN(paymentDto, IBAN);
        return ResponseEntity.ok(payment);
    }

//---------------------------------------------------

    //pay bill by id of charge

    //pay bill by regular payment id


    //check if paid

    //deletion 9of payment

    //receive all payments by client

    //receive all payments by receiver

    //receive all charges by regular payment
}
