package com.shoppingcart.app.exception;

public class InvalidLoginRequestException extends RuntimeException {
    
    public InvalidLoginRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
