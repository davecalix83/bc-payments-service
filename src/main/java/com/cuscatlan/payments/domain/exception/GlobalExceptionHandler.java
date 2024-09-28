package com.cuscatlan.payments.domain.exception;

import feign.FeignException;
import feign.RetryableException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for managing exceptions across the application.
 * Provides specific handling for various types of exceptions and returns appropriate error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions related to payment processing.
     * 
     * @param ex the {@link PaymentProcessingException} to handle
     * @return a {@link ResponseEntity} containing the error response with status 500 (Internal Server Error)
     */
    @ExceptionHandler({PaymentProcessingException.class})
    public ResponseEntity<ErrorResponse> handlePaymentExceptions(PaymentProcessingException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions when a payment is not found.
     * 
     * @param ex the {@link PaymentNotFoundException} to handle
     * @return a {@link ResponseEntity} containing the error response with status 404 (Not Found)
     */
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions when an order is not found.
     * 
     * @param ex the {@link OrderNotFoundException} to handle
     * @return a {@link ResponseEntity} containing the error response with status 404 (Not Found)
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions related to external service communication, such as Feign and Retryable exceptions.
     * 
     * @param ex the exception to handle, either {@link RetryableException} or {@link FeignException}
     * @return a {@link ResponseEntity} containing the error response with status 503 (Service Unavailable) or 502 (Bad Gateway)
     */
    @ExceptionHandler({RetryableException.class, FeignException.class})
    public ResponseEntity<ErrorResponse> handleServiceUnavailableException(Exception ex) {
        String message;
        HttpStatus status;

        if (ex instanceof RetryableException) {
            message = "The service is currently unavailable. Please try again later.";
            status = HttpStatus.SERVICE_UNAVAILABLE;
        } else {
            message = "An error occurred while trying to communicate with the order service.";
            status = HttpStatus.BAD_GATEWAY;
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("SERVICE_UNAVAILABLE");
        errorResponse.setMessage(message);
        errorResponse.setProcessedAt(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, status);
    }
}
