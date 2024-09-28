package com.cuscatlan.payments.application.controller;

import com.cuscatlan.payments.application.dto.PaymentDto;
import com.cuscatlan.payments.application.dto.PaymentRequestDto;
import com.cuscatlan.payments.application.dto.PaymentResponseDto;
import com.cuscatlan.payments.application.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling payment-related operations.
 * Provides endpoints for processing payments and retrieving payment status.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Processes a payment request.
     * This endpoint accepts a payment request in the form of a {@link PaymentRequestDto}
     * and returns a {@link PaymentResponseDto} containing the result of the payment process.
     *
     * @param paymentRequestDto the payment request data
     * @return a {@link ResponseEntity} containing the payment response and a status of 201 CREATED
     */
    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDto> processPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto response = paymentService.processPayment(paymentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves the status of a payment by its ID.
     * This endpoint returns a {@link PaymentDto} with the current status of the payment.
     *
     * @param paymentId the ID of the payment to retrieve
     * @return a {@link ResponseEntity} containing the payment status and a status of 200 OK
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> getPaymentStatus(@PathVariable Long paymentId) {
        PaymentDto response = paymentService.getPaymentStatus(paymentId);
        return ResponseEntity.ok(response);
    }

}
