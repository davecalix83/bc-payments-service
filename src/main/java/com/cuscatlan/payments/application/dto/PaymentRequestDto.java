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

    @Schema(description = "ID of the associated order", example = "12345")
    private Long orderId;

    @Schema(description = "Amount of the payment", example = "99.99", required = true)
    private BigDecimal amount;

    @Schema(description = "Currency of the payment", example = "USD", allowableValues = {"USD", "EUR", "GBP"})
    private String currency;
}
