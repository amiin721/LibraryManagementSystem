package com.incubyte.assessment.exception;

/**
 * Custom exception class to handle specific error cases in the library system.
 * This class extends RuntimeException and allows the application to throw exceptions
 * with a custom error message.
 *
 * @Usage:
 * This exception is used when a specific error occurs in the application, such as attempting to
 * add a book that already exists or borrowing a book that is unavailable.
 * The exception message provides detailed information about the error.
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
