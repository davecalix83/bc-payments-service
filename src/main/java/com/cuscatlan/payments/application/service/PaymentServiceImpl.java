package com.cuscatlan.payments.application.service;

import com.cuscatlan.payments.application.dto.*;
import com.cuscatlan.payments.domain.exception.OrderNotFoundException;
import com.cuscatlan.payments.domain.exception.PaymentNotFoundException;
import com.cuscatlan.payments.domain.exception.PaymentProcessingException;
import com.cuscatlan.payments.domain.model.BillingAddress;
import com.cuscatlan.payments.domain.model.CardDetails;
import com.cuscatlan.payments.domain.model.Payment;
import com.cuscatlan.payments.infrastructure.external.OrderServiceClient;
import com.cuscatlan.payments.infrastructure.repository.PaymentRepository;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            return createProcessPaymentResponse(savedPayment);

        } catch (PaymentProcessingException e) {
            return createErrorResponse("ERROR", e.getMessage());
        }
    }

    private PaymentResponseDto createProcessPaymentResponse(Payment savedPayment) {
        return PaymentResponseDto
                .builder()
                .paymentId(savedPayment.getId())
                .status(savedPayment.getStatus())
                .message(savedPayment.getDescription())
                .processedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public PaymentDto getPaymentStatus(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        return createPaymentResponse(payment);
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

        Payment payment = Payment.builder()
                .orderId(paymentRequestDto.getOrderId())
                .amount(paymentRequestDto.getAmount())
                .currency(paymentRequestDto.getCurrency())
                .paymentMethod(paymentRequestDto.getPaymentMethod())
                .customerId(paymentRequestDto.getCustomerId())
                .email(paymentRequestDto.getEmail())
                .description(paymentRequestDto.getDescription())
                .transactionId(paymentRequestDto.getTransactionId())
                .status(paymentRequestDto.getPaymentStatus())
                .createdAt(LocalDateTime.now())
                .build();

        CardDetails cardDetails = CardDetails.builder()
                .cardNumber(paymentRequestDto.getCardDetails().getCardNumber())
                .cardHolderName(paymentRequestDto.getCardDetails().getCardHolderName())
                .expirationDate(paymentRequestDto.getCardDetails().getExpirationDate())
                .securityCode(paymentRequestDto.getCardDetails().getSecurityCode())
                .build();
        payment.setCardDetails(cardDetails);

        BillingAddress billingAddress = BillingAddress.builder()
                .street(paymentRequestDto.getBillingAddress().getStreet())
                .city(paymentRequestDto.getBillingAddress().getCity())
                .state(paymentRequestDto.getBillingAddress().getState())
                .postalCode(paymentRequestDto.getBillingAddress().getPostalCode())
                .country(paymentRequestDto.getBillingAddress().getCountry())
                .build();
        payment.setBillingAddress(billingAddress);

        return payment;
    }


    private void updateOrderStatus(Long orderId, String paymentStatus) {
        try {

            OrderDto orderDto = orderServiceClient.getOrderById(orderId).getBody();

            if (orderDto == null) {
                throw new OrderNotFoundException(orderId);
            }

            orderDto.setStatus(paymentStatus);
            orderServiceClient.updateOrder(orderDto);

        } catch (PaymentProcessingException e) {
            throw new PaymentProcessingException("Failed to update order status");
        }
    }

    private PaymentDto createPaymentResponse(Payment payment) {
        return PaymentDto.builder()
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .cardDetails(CardDetailsDto.builder()
                        .cardNumber(payment.getCardDetails().getCardNumber())
                        .cardHolderName(payment.getCardDetails().getCardHolderName())
                        .expirationDate(payment.getCardDetails().getExpirationDate())
                        .securityCode(payment.getCardDetails().getSecurityCode())
                        .build())
                .billingAddress(BillingAddressDto.builder()
                        .street(payment.getBillingAddress().getStreet())
                        .city(payment.getBillingAddress().getCity())
                        .state(payment.getBillingAddress().getState())
                        .postalCode(payment.getBillingAddress().getPostalCode())
                        .country(payment.getBillingAddress().getCountry())
                        .build())
                .customerId(payment.getCustomerId())
                .email(payment.getEmail())
                .description(payment.getDescription())
                .transactionId(payment.getTransactionId())
                .paymentStatus(payment.getStatus())
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
