package com.cuscatlan.payments.domain.exception;

import java.time.LocalDateTime;

import feign.FeignException;
import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PaymentProcessingException.class})
    public ResponseEntity<ErrorResponse> handlePaymentExceptions(PaymentProcessingException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

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
