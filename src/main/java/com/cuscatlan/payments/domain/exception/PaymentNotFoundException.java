package com.cuscatlan.payments.domain.exception;

/**
 * Exception thrown when a payment is not found in the system.
 * This exception extends {@link RuntimeException} and provides a
 * specific error message indicating the missing payment's ID.
 */
public class PaymentNotFoundException extends RuntimeException {

    /**
     * Constructs a new PaymentNotFoundException with a detailed message.
     *
     * @param paymentId the ID of the payment that was not found
     */
    public PaymentNotFoundException(Long paymentId) {
        super("Payment not found: " + paymentId);
    }
}
