package com.cuscatlan.payments.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String transactionId;
    private String email;
    private Long customerId;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    @Embedded
    private CardDetails cardDetails;

    @Embedded
    private BillingAddress billingAddress;

}
