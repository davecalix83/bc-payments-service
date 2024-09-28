package com.cuscatlan.payments.application.service;

import com.cuscatlan.payments.application.dto.OrderDto;
import com.cuscatlan.payments.domain.exception.OrderNotFoundException;
import com.cuscatlan.payments.domain.exception.PaymentProcessingException;
import com.cuscatlan.payments.infrastructure.external.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusUpdaterService {

    private final OrderServiceClient orderServiceClient;

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
