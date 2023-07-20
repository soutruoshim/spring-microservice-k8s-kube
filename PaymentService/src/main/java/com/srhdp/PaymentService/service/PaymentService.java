package com.srhdp.PaymentService.service;

import com.srhdp.PaymentService.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
