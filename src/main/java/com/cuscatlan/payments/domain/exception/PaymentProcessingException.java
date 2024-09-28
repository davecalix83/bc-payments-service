package com.cuscatlan.payments.domain.exception;

/**
 * Exception thrown when an error occurs during the payment processing.
 * This exception extends {@link RuntimeException} and allows for
 * conveying specific error messages related to payment processing failures.
 */
public class PaymentProcessingException extends RuntimeException {

    /**
     * Constructs a new PaymentProcessingException with a specified message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public PaymentProcessingException(String message) {
        super(message);
    }
}
