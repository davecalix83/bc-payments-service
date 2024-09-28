package com.cuscatlan.payments.application.service;

import java.util.Random;
import org.springframework.stereotype.Component;

/**
 * Service responsible for randomly determining the status of a payment.
 * It simulates payment processing outcomes by randomly selecting a status.
 */
@Component
public class RandomPaymentStatusHandlerService {

    /**
     * Determines the payment status randomly.
     * This method returns either "COMPLETED" or "FAILED" based on a random boolean value.
     * 
     * @return a String representing the payment status, either "COMPLETED" or "FAILED"
     */
    public String determineStatus() {
        return new Random().nextBoolean() ? "COMPLETED" : "FAILED";
    }
}
