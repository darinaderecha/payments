package com.privat.payments.controller;

import com.privat.payments.model.Charge;
import com.privat.payments.model.Payment;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {

    public List<Payment> getRegularPaymentsByClient(){
        return Collections.emptyList();
    }

    public Map<Payment, Charge> getAllChargesByClient(){
        return new HashMap<>();
    }

    public List<Payment> getRegularPaymentsByReceiver(){
        return Collections.emptyList();
    }

    public Map<Payment, Charge> getAllChargesByReceiver(){
        return new HashMap<>();
    }

    public List<Charge> getHistoryByPayment(){
        return Collections.emptyList();
    }
}
