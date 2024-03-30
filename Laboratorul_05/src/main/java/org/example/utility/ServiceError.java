package org.example.utility;


/**
 * Custom exception class for handling common exceptions within the application.
 * Extends the base Exception class.
 */
public class ServiceError extends RuntimeException {

    /**
     * Constructs a new CommonException with the specified message.
     *
     * @param message the detail message (it can be returned by the getMessage() function)
     */
    public ServiceError(String message) {
        super((message == null) ? "Error" : message);
    }
}