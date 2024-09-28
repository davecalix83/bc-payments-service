package com.cuscatlan.payments.domain.exception;

/**
 *
 * @author hguzman
 */
public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(Long paymentId) {
        super("Payment not found: " + paymentId);
    }
}
