package com.my.project.cryptoservice.validation;

import com.my.project.cryptoservice.exception.CryptoValidatorException;
import com.my.project.cryptoservice.exception.NotAvailableCryptoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CryptoValidatorTest {

    private final CryptoValidator cryptoValidator = new CryptoValidator("BTC,ETH,DOGE");

    @Test
    public void test_validateCryptoSupporting_Successful() {
        // given
        String crypto = "BTC";

        // when
        cryptoValidator.validateCryptoSupporting(crypto);
    }

    @Test
    public void test_validateCryptoSupporting_Unsuccessful() {
        // given
        String crypto = "ADA";

        // when
        assertThrows(NotAvailableCryptoException.class, () -> cryptoValidator.validateCryptoSupporting(crypto));
    }

    @Test
    public void test_validatePeriod_Successful() {
        // given
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, 1, 2, 0, 0, 0);

        // when
        cryptoValidator.validatePeriod(startDate, endDate);
    }

    @Test
    public void test_validatePeriod_Unsuccessful() {
        // given
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 2, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        // when
        assertThrows(CryptoValidatorException.class, () -> cryptoValidator.validatePeriod(startDate, endDate));
    }
}
