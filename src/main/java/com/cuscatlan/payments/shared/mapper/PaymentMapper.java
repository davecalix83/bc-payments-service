package com.cuscatlan.payments.shared.mapper;

import com.cuscatlan.payments.application.dto.PaymentDto;
import com.cuscatlan.payments.domain.model.Payment;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between {@link Payment} entities and {@link PaymentDto} objects.
 * This class extends {@link AbstractGenericMapper} to provide the mapping functionality.
 */
@Component
public class PaymentMapper extends AbstractGenericMapper<Payment, PaymentDto> {

    /**
     * Constructor that initializes the mapper with the Payment entity and PaymentDto classes.
     */
    public PaymentMapper() {
        super(Payment.class, PaymentDto.class);
    }
}
