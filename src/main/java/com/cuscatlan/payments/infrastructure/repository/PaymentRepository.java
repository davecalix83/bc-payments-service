package com.cuscatlan.payments.infrastructure.repository;

import com.cuscatlan.payments.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
