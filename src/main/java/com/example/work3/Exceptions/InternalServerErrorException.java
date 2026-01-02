package com.example.work3.Exceptions;

public class InternalServerErrorException extends RuntimeException { // Mude para RuntimeException
    public InternalServerErrorException(String message) {
        super(message);
    }

    // P/ aceitar a causa (a exceção original)
    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}