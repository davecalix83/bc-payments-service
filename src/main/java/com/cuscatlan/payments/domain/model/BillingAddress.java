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
public class BillingAddress {

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
