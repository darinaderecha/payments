package com.privat.payments.controller;

import com.privat.payments.dto.PaymentCreateDto;
import com.privat.payments.model.Charge;
import com.privat.payments.model.Payment;
import com.privat.payments.service.RegularPaymentsService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    @PostMapping("/charge")
    public ResponseEntity<Charge> makeCharge(@RequestBody UUID paymentId) {
        Charge charge = paymentsService.makeCharge(paymentId);
        return ResponseEntity.ok(charge);
    }
    //2. validate time when tou have to withdraw ansd send time


//---------------------------------------------------

    //pay bill by id of charge

    //pay bill by regular payment id


    //check if paid

    //deletion 9of payment

    //receive all payments by client

    //receive all payments by receiver

    //receive all charges by regular payment
}
