package com.cuscatlan.payments.application.service;

import com.cuscatlan.payments.application.dto.OrderDto;
import com.cuscatlan.payments.application.dto.PaymentRequestDto;
import com.cuscatlan.payments.application.dto.PaymentResponseDto;
import com.cuscatlan.payments.domain.exception.ExternalServiceException;
import com.cuscatlan.payments.domain.exception.PaymentProcessingException;
import com.cuscatlan.payments.domain.model.Payment;
import com.cuscatlan.payments.infrastructure.external.OrderServiceClient;
import com.cuscatlan.payments.infrastructure.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderServiceClient orderServiceClient;
    private final PaymentStatusHandler paymentStatusHandler;

    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto) {
        
        try {
            validateOrder(paymentRequestDto.getOrderId());

            Payment payment = createPayment(paymentRequestDto);
            payment.setStatus(paymentStatusHandler.determineStatus());
            Payment savedPayment = paymentRepository.save(payment);
            updateOrderStatus(paymentRequestDto.getOrderId(), payment.getStatus());

            return createPaymentResponse(savedPayment);
        } catch (PaymentProcessingException | ExternalServiceException e) {
            return createErrorResponse("ERROR", e.getMessage());
        } catch (Exception e) {
            return createErrorResponse("ERROR", "An unexpected error occurred.");
        }
    }

    @Override
    public PaymentResponseDto getPaymentStatus(Long paymentId) {
        try {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new PaymentProcessingException("Payment not found for ID: " + paymentId));
            return createPaymentResponse(payment);
        } catch (PaymentProcessingException e) {
            return createErrorResponse("ERROR", e.getMessage());
        } catch (Exception e) {
            return createErrorResponse("ERROR", "An unexpected error occurred while retrieving payment status.");
        }
    }

    private void validateOrder(Long orderId) {
        OrderDto orderDto = orderServiceClient.getOrderById(orderId).getBody();
        if (orderDto == null) {
            throw new PaymentProcessingException("Order not found for ID: " + orderId);
        }
        if ("PAID".equalsIgnoreCase(orderDto.getStatus())) {
            throw new PaymentProcessingException("Order has already been paid.");
        }
    }

    private Payment createPayment(PaymentRequestDto paymentRequestDto) {
        return Payment
                .builder()
                .orderId(paymentRequestDto.getOrderId())
                .amount(paymentRequestDto.getAmount())
                .currency(paymentRequestDto.getCurrency())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private void updateOrderStatus(Long orderId, String paymentStatus) {
        try {
            OrderDto orderDto = orderServiceClient.getOrderById(orderId).getBody();
            orderDto.setStatus(paymentStatus);
            orderServiceClient.updateOrder(orderDto);
        } catch (ExternalServiceException e) {
            throw new PaymentProcessingException("Failed to update order status");
        }
    }

    private PaymentResponseDto createPaymentResponse(Payment payment) {
        return PaymentResponseDto
                .builder()
                .paymentId(payment.getId())
                .status(payment.getStatus())
                .processedAt(payment.getCreatedAt())
                .message("Payment processed successfully.")
                .build();
    }

    private PaymentResponseDto createErrorResponse(String status, String message) {
        return PaymentResponseDto
                .builder()
                .paymentId(null)
                .status(status)
                .message(message)
                .processedAt(LocalDateTime.now())
                .build();
    }
}
