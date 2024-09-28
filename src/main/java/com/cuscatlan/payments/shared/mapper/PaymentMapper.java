package com.cuscatlan.payments.shared.mapper;

import com.cuscatlan.payments.application.dto.PaymentDto;
import com.cuscatlan.payments.application.dto.PaymentRequestDto;
import com.cuscatlan.payments.domain.model.Payment;
import org.springframework.stereotype.Component;

/**
 *
 * @author hguzman
 */
@Component
public class PaymentMapper extends AbstractGenericMapper<Payment, PaymentDto> {

    public PaymentMapper() {
        super(Payment.class, PaymentDto.class);
    }
}
