package com.cuscatlan.payments.infrastructure.repository;

import com.cuscatlan.payments.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Payment} entities.
 * This interface extends JpaRepository, providing CRUD operations
 * for Payment objects, including methods for saving, deleting, and
 * finding payments by their ID.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {}
