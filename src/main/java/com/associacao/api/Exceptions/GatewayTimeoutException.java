package com.associacao.api.Exceptions;

public class GatewayTimeoutException extends Exception {
    public GatewayTimeoutException(String message) {
        super(message);
    }
}
