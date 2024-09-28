package com.cuscatlan.payments.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Details of the payment request")
public class PaymentRequestDto {

    @Schema(
            description = "Order ID", 
            example = "1")
    private Long orderId;

    @Schema(
            description = "Payment amount", 
            example = "100.00")
    private BigDecimal amount;

    @Schema(
            description = "Currency of the payment", 
            example = "USD", 
            allowableValues = {"USD", "EUR", "GBP", "JPY"})
    private String currency;

    @Schema(
            description = "Payment method", 
            example = "CREDIT_CARD", 
            allowableValues = {"CREDIT_CARD", "DEBIT_CARD", "PAYPAL", "BANK_TRANSFER"})
    private String paymentMethod;

    @Schema(description = "Credit card details")
    private CardDetailsDto cardDetails;

    @Schema(
            description = "Billing address")
    private BillingAddressDto billingAddress;

    @Schema(
            description = "Customer ID", 
            example = "12345")
    private Long customerId;

    @Schema(
            description = "Customer's email", 
            example = "davecalix@gmail.com")
    private String email;

    @Schema(
            description = "Payment description", 
            example = "Payment for product purchase")
    private String description;

    @Schema(
            description = "Transaction ID", 
            example = "1")
    private String transactionId;

    @Schema(
            description = "Payment status", 
            example = "PENDING", 
            allowableValues = {"PENDING", "COMPLETED", "FAILED", "REFUNDED"})
    private String paymentStatus;
}
