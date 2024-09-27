package com.cuscatlan.payments.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long orderId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private CardDetailsDto cardDetails;
    private BillingAddressDto billingAddress;
    private Long customerId;
    private String email;
    private String description;
    private String transactionId;
    private String paymentStatus;
}
