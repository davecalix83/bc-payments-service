package com.cuscatlan.payments.application.service;

import com.cuscatlan.payments.application.dto.OrderDto;
import com.cuscatlan.payments.domain.exception.PaymentProcessingException;
import com.cuscatlan.payments.infrastructure.external.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Service responsible for validating the status of an order before processing a
 * payment. It interacts with an external service to retrieve order details and
 * ensures the order is valid for payment processing.
 */
@Component
@RequiredArgsConstructor
public class OrderValidatorService {

    private final OrderServiceClient orderServiceClient;

    /**
     * Validates the order by its ID. This method checks if the order exists and
     * if it is eligible for payment processing.
     *
     * @param orderId the ID of the order to validate
     * @return an OrderDto containing the order details
     * @throws PaymentProcessingException if the order is not found or has
     * already been paid
     */
    public OrderDto validateOrder(Long orderId) {
        OrderDto orderDto = orderServiceClient.getOrderById(orderId).getBody();
        if (orderDto == null) {
            throw new PaymentProcessingException("Order not found for ID: " + orderId);
        }
        if ("PAID".equalsIgnoreCase(orderDto.getStatus())) {
            throw new PaymentProcessingException("Order has already been paid.");
        }
        return orderDto;
    }
}
