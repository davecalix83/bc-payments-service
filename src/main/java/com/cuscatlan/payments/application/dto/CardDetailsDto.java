package com.cuscatlan.payments.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDetailsDto {

    @Schema(
            description = "Credit card number",
            example = "4111111111111111")
    private String cardNumber;

    @Schema(
            description = "Name of the cardholder",
            example = "David Guzman")
    private String cardHolderName;

    @Schema(
            description = "Expiration date of the card in MM/YY format",
            example = "12/25")
    private String expirationDate;

    @Schema(
            description = "Security code (CVV) of the card",
            example = "123")
    private String securityCode;
}
