package com.srhdp.PaymentService.service;

import com.srhdp.PaymentService.model.PaymentRequest;
import com.srhdp.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
