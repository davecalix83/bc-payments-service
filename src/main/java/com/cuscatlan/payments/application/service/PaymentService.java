package com.cuscatlan.payments.application.service;

import com.cuscatlan.payments.application.dto.PaymentDto;
import com.cuscatlan.payments.application.dto.PaymentRequestDto;
import com.cuscatlan.payments.application.dto.PaymentResponseDto;

/**
 * Interface defining the contract for payment services.
 * Provides methods to process payments and retrieve payment status.
 */
public interface PaymentService {

    /**
     * Processes a payment based on the provided payment request details.
     * 
     * @param paymentRequestDto the DTO containing payment request details such as order ID, amount, and payment method
     * @return a PaymentResponseDto with details about the processed payment, including status and processed time
     */
    PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto);

    /**
     * Retrieves the current status of a payment by its ID.
     * 
     * @param paymentId the ID of the payment to retrieve the status for
     * @return a PaymentDto containing the current status and details of the payment
     */
    PaymentDto getPaymentStatus(Long paymentId);
}
