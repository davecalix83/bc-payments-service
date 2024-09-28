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

/**
 * Implementation of the PaymentService interface that handles payment processing and status retrieval.
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderStatusUpdaterService orderStatusUpdaterService;
    private final OrderValidatorService orderValidatorService;
    private final RandomPaymentStatusHandlerService randomPaymentStatusHandlerService;
    private final PaymentMapper paymentMapper;

    /**
     * Processes a payment based on the provided PaymentRequestDto.
     * This method validates the order, creates a Payment entity, and updates the payment status.
     * 
     * @param paymentRequestDto the DTO containing payment request details
     * @return a PaymentResponseDto with payment result details
     * @throws PaymentProcessingException if any issue occurs during payment processing
     */
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

    /**
     * Creates a PaymentResponseDto with details about the processed payment.
     * 
     * @param savedPayment the Payment entity that was processed and saved
     * @return a PaymentResponseDto containing the payment details
     */
    private PaymentResponseDto createProcessPaymentResponse(@NotNull Payment savedPayment) {
        return PaymentResponseDto
                .builder()
                .paymentId(savedPayment.getId())
                .status(savedPayment.getStatus())
                .message(savedPayment.getDescription())
                .processedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Retrieves the status of a payment by its ID.
     * 
     * @param paymentId the ID of the payment to retrieve
     * @return a PaymentDto containing the payment status details
     * @throws PaymentNotFoundException if no payment with the specified ID is found
     */
    @Override
    public PaymentDto getPaymentStatus(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        return createPaymentResponse(payment);
    }

    /**
     * Creates a Payment entity from the provided PaymentRequestDto and OrderDto.
     * 
     * @param paymentRequestDto the DTO containing the payment request details
     * @param orderDto the DTO containing the order details
     * @return a Payment entity representing the payment to be processed
     */
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

    /**
     * Builds a description for the payment based on the payment request and order details.
     * 
     * @param paymentRequestDto the DTO containing the payment request details
     * @param orderDto the DTO containing the order details
     * @return a string description for the payment
     */
    private @NotNull String buildDescription(@NotNull PaymentRequestDto paymentRequestDto, @NotNull OrderDto orderDto) {
        return paymentRequestDto.getDescription() + orderDto.getId();
    }

    /**
     * Creates a CardDetails entity from the provided PaymentRequestDto.
     * 
     * @param paymentRequestDto the DTO containing the card details
     * @return a CardDetails entity containing the card information
     */
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

    /**
     * Creates a BillingAddress entity from the provided PaymentRequestDto.
     * 
     * @param paymentRequestDto the DTO containing the billing address details
     * @return a BillingAddress entity containing the billing address information
     */
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

    /**
     * Creates a PaymentDto from a Payment entity.
     * 
     * @param payment the Payment entity to be mapped to a DTO
     * @return a PaymentDto containing the payment details
     */
    private PaymentDto createPaymentResponse(Payment payment) {
        return paymentMapper.toDto(payment);
    }
}
