package com.my.project.cryptoservice.exception;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotAvailableCryptoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorInfo> handleNotAvailableCryptoException(NotAvailableCryptoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorInfo(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(CryptoValidatorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorInfo> handleCryptoValidatorException(CryptoValidatorException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorInfo(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorInfo> handleRuntimeException(RuntimeException e) {
        log.error(e.getStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong."));
    }

    @Data
    private static class ErrorInfo {
        private final int statusCode;
        private final String message;
    }
}
