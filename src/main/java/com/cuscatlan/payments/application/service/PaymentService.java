package com.cuscatlan.payments.application.service;

import com.cuscatlan.payments.application.dto.PaymentRequestDto;
import com.cuscatlan.payments.application.dto.PaymentResponseDto;

public interface PaymentService {
    
    PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto);

    PaymentResponseDto getPaymentStatus(Long paymentId);
}
