package com.cuscatlan.payments.application.service;

import com.cuscatlan.payments.application.dto.*;
import com.cuscatlan.payments.domain.exception.PaymentNotFoundException;
import com.cuscatlan.payments.domain.exception.PaymentProcessingException;
import com.cuscatlan.payments.domain.model.BillingAddress;
import com.cuscatlan.payments.domain.model.CardDetails;
import com.cuscatlan.payments.domain.model.Payment;
import com.cuscatlan.payments.infrastructure.repository.PaymentRepository;
import com.cuscatlan.payments.shared.mapper.PaymentMapper;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderStatusUpdaterService orderStatusUpdaterService;
    private final OrderValidatorService orderValidatorService;
    private final RandomPaymentStatusHandlerService randomPaymentStatusHandlerService;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDto processPayment(@NotNull PaymentRequestDto paymentRequestDto) {

        try {

            OrderDto orderDto = orderValidatorService.validateOrder(paymentRequestDto.getOrderId());

            Payment payment = createPayment(paymentRequestDto, orderDto);
            payment.setStatus(randomPaymentStatusHandlerService.determineStatus());
            Payment savedPayment = paymentRepository.save(payment);
            orderStatusUpdaterService.updateOrderStatus(paymentRequestDto.getOrderId(), payment.getStatus());
            return createProcessPaymentResponse(savedPayment);

        } catch (PaymentProcessingException e) {
            throw new PaymentProcessingException(e.getMessage());
        }
    }

    private PaymentResponseDto createProcessPaymentResponse(@NotNull Payment savedPayment) {
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

    private Payment createPayment(@NotNull PaymentRequestDto paymentRequestDto, @NotNull OrderDto orderDto) {
        return Payment
                .builder()
                .orderId(orderDto.getId())
                .amount(orderDto.getTotalAmount())
                .currency(paymentRequestDto.getCurrency())
                .paymentMethod(paymentRequestDto.getPaymentMethod())
                .customerId(orderDto.getCustomerId())
                .email(paymentRequestDto.getEmail())
                .description(buildDescription(paymentRequestDto, orderDto))
                .transactionId(paymentRequestDto.getTransactionId())
                .status(paymentRequestDto.getPaymentStatus())
                .createdAt(LocalDateTime.now())
                .cardDetails(createCardDetails(paymentRequestDto))
                .billingAddress(createBillingAddress(paymentRequestDto))
                .build();
    }

    private @NotNull String buildDescription(@NotNull PaymentRequestDto paymentRequestDto, @NotNull OrderDto orderDto) {
        return paymentRequestDto.getDescription() + orderDto.getId();
    }

    private CardDetails createCardDetails(@NotNull PaymentRequestDto paymentRequestDto) {
        CardDetailsDto cardDetailsDto = paymentRequestDto.getCardDetails();
        return CardDetails
                .builder()
                .cardNumber(cardDetailsDto.getCardNumber())
                .cardHolderName(cardDetailsDto.getCardHolderName())
                .expirationDate(cardDetailsDto.getExpirationDate())
                .securityCode(cardDetailsDto.getSecurityCode())
                .build();
    }

    private BillingAddress createBillingAddress(@NotNull PaymentRequestDto paymentRequestDto) {
        BillingAddressDto billingAddressDto = paymentRequestDto.getBillingAddress();
        return BillingAddress
                .builder()
                .street(billingAddressDto.getStreet())
                .city(billingAddressDto.getCity())
                .state(billingAddressDto.getState())
                .postalCode(billingAddressDto.getPostalCode())
                .country(billingAddressDto.getCountry())
                .build();
    }


    private PaymentDto createPaymentResponse(Payment payment) {

        return paymentMapper.toDto(payment);

    }
}
