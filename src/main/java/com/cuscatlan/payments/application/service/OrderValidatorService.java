package com.cuscatlan.payments.application.service;

import com.cuscatlan.payments.application.dto.OrderDto;
import com.cuscatlan.payments.domain.exception.PaymentProcessingException;
import com.cuscatlan.payments.infrastructure.external.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidatorService {

    private final OrderServiceClient orderServiceClient;

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
