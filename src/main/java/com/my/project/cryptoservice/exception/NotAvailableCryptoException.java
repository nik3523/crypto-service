package com.my.project.cryptoservice.exception;

public class NotAvailableCryptoException extends RuntimeException {
    public NotAvailableCryptoException(String message) {
        super(message);
    }
}
