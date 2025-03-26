package org.anshu.bookstore.exception;

// Custom exception for resource not found scenarios
@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {
    
    // Constructor to create an exception with a custom message
    public ResourceNotFoundException(String message) {
        super(message);
    }
}