package com.cuscatlan.payments.domain.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a standardized error response structure for the application.
 * This class encapsulates the details of an error that occurs during processing,
 * including the status, error message, and timestamp of when the error was processed.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /**
     * The status of the error, typically a string indicating the type of error.
     */
    private String status;

    /**
     * A descriptive message providing details about the error.
     */
    private String message;

    /**
     * The timestamp indicating when the error was processed.
     */
    private LocalDateTime processedAt;
}
