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
public class BillingAddressDto {

    @Schema(
            description = "Street address of the billing location",
            example = "Barrio Concepción 2da Avenida Comayagüela, Tegucigalpa, Honduras")
    private String street;

    @Schema(
            description = "City of the billing location",
            example = "Tegucigalpa")
    private String city;

    @Schema(
            description = "State or province of the billing location",
            example = "FM")
    private String state;

    @Schema(
            description = "Postal code of the billing address",
            example = "11101")
    private String postalCode;

    @Schema(
            description = "Country of the billing address",
            example = "HONDURAS")
    private String country;
}
