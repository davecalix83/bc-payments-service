package com.cuscatlan.payments.domain.exception;

/**
 * Exception thrown when an order is not found in the system.
 * This exception extends {@link RuntimeException} and provides a
 * specific error message indicating the missing order's ID.
 */
public class OrderNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new OrderNotFoundException with a detailed message.
     *
     * @param orderId the ID of the order that was not found
     */
    public OrderNotFoundException(Long orderId) {
        super("Order not found: " + orderId);
    }
}
