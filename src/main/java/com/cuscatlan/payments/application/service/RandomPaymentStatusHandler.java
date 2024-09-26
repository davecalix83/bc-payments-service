package com.cuscatlan.payments.application.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomPaymentStatusHandler implements PaymentStatusHandler{

    @Override
    public String determineStatus() {
        return new Random().nextBoolean() ? "COMPLETED" : "FAILED";
    }
}