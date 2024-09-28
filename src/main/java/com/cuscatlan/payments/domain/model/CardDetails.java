package com.cuscatlan.payments.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CardDetails {

    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String securityCode;
}
