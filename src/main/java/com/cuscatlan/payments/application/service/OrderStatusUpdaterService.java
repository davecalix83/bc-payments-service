package com.cuscatlan.payments.application.service;

import com.cuscatlan.payments.application.dto.OrderDto;
import com.cuscatlan.payments.domain.exception.OrderNotFoundException;
import com.cuscatlan.payments.domain.exception.PaymentProcessingException;
import com.cuscatlan.payments.infrastructure.external.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Service responsible for updating the status of an order after payment processing.
 * It retrieves the order details and modifies the order status based on the payment outcome.
 */
@Component
@RequiredArgsConstructor
public class OrderStatusUpdaterService {

    private final OrderServiceClient orderServiceClient;

    /**
     * Updates the status of an order based on the payment result.
     * If the payment failed, the order status remains unchanged; otherwise, it is updated to "PAID".
     * 
     * @param orderId the ID of the order to update
     * @param paymentStatus the status of the payment (either "FAILED" or another status indicating success)
     * @throws OrderNotFoundException if the order with the specified ID is not found
     * @throws PaymentProcessingException if there is an error updating the order status
     */
    public void updateOrderStatus(Long orderId, String paymentStatus) {
        OrderDto orderDto = orderServiceClient.getOrderById(orderId).getBody();
        if (orderDto == null) {
            throw new OrderNotFoundException(orderId);
        }

        orderDto.setStatus(paymentStatus.equalsIgnoreCase("FAILED") ? orderDto.getStatus() : "PAID");

        try {
            orderServiceClient.updateOrder(orderDto);
        } catch (Exception e) {
            throw new PaymentProcessingException("Failed to update order status for order ID: " + orderId);
        }
    }
}
