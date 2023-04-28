package com.my.project.cryptoservice.validation;

import com.my.project.cryptoservice.exception.CryptoValidatorException;
import com.my.project.cryptoservice.exception.NotAvailableCryptoException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Log4j2
public class CryptoValidator {

    public CryptoValidator(@Value(value = "${cryptos.available}") String availableCryptos) {
        this.availableCryptos = availableCryptos;
    }

    private final String availableCryptos;

    public void validateCryptoSupporting(String crypto) {
        boolean isValid = Arrays.stream(availableCryptos.split(",")).anyMatch(crypto::equalsIgnoreCase);
        if (!isValid) {
            log.error("Trying to request unavailable Crypto: " + crypto);
            throw new NotAvailableCryptoException("Crypto [" + crypto + "] is not supported.");
        }
    }

    public void validatePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            log.error("Start date is after end date: {} > {}", startDate, endDate);
            throw new CryptoValidatorException("Start date is after end date: " + startDate + " > " + endDate);
        }
    }
}
