package com.my.project.cryptoservice.service;

import com.my.project.cryptoservice.dto.CryptoMetrics;
import com.my.project.cryptoservice.dto.CryptoNormalizedRange;
import com.my.project.cryptoservice.entity.Crypto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.my.project.cryptoservice.util.DateTimeUtils.getFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CryptoStatisticServiceTest {

    private static final DateTimeFormatter DATE_FORMATTER = getFormatter();

    @InjectMocks
    private CryptoStatisticService cryptoStatisticService;

    @Mock
    private CryptoService cryptoService;

    @Mock
    private CryptoProcessor cryptoProcessor;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(cryptoService, cryptoProcessor);
    }

    @Test
    public void test_GetMetricsForAll() {
        // given
        Crypto crypto = new Crypto();
        crypto.setName("BTC");
        crypto.setPrice(100);
        CryptoMetrics expectedCryptoMetric = getExpectedCryptoMetric();
        String startDateTime = "2021-01-01T00:00:00";
        String endDateTime = "2021-01-01T00:00:00";

        when(cryptoService.getAllInPeriod(startDateTime, endDateTime)).thenReturn(List.of(crypto));
        when(cryptoProcessor.getMetricsForCryptos(List.of(crypto))).thenReturn(List.of(expectedCryptoMetric));

        // when
        List<CryptoMetrics> result = cryptoStatisticService.getMetricsForAll(startDateTime, endDateTime);

        // then
        assertEquals(1, result.size());
        assertEquals(expectedCryptoMetric, result.get(0));
        verify(cryptoService).getAllInPeriod(startDateTime, endDateTime);
        verify(cryptoProcessor).getMetricsForCryptos(List.of(crypto));
    }

    @Test
    public void test_getMetricsForCrypto() {
        // given
        Crypto crypto = new Crypto();
        crypto.setName("BTC");
        crypto.setPrice(100);
        CryptoMetrics expectedCryptoMetric = getExpectedCryptoMetric();
        String startDateTime = "2021-01-01T00:00:00";
        String endDateTime = "2021-01-01T00:00:00";

        when(cryptoService.getByNameInPeriod("BTC", startDateTime, endDateTime)).thenReturn(List.of(crypto));
        when(cryptoProcessor.getMetricsForCrypto("BTC", List.of(crypto))).thenReturn(expectedCryptoMetric);

        // when
        List<CryptoMetrics> result = cryptoStatisticService.getMetricsForCrypto("BTC", startDateTime, endDateTime);

        // then
        assertEquals(1, result.size());
        assertEquals(expectedCryptoMetric, result.get(0));
        verify(cryptoService).getByNameInPeriod("BTC", startDateTime, endDateTime);
        verify(cryptoProcessor).getMetricsForCrypto("BTC", List.of(crypto));
    }

    @Test
    public void test_GetNormalizedRangeAscendingOrder() {
        // given
        Crypto crypto = new Crypto();
        crypto.setName("BTC");
        crypto.setPrice(100);
        CryptoNormalizedRange expectedNormalizedRange = new CryptoNormalizedRange("BTC", 2.0);
        CryptoNormalizedRange expectedNormalizedRange2 = new CryptoNormalizedRange("BTC", 3.0);

        String startDateTime = "2021-01-01T00:00:00";
        String endDateTime = "2021-01-01T00:00:00";

        when(cryptoService.getAllInPeriod(startDateTime, endDateTime)).thenReturn(List.of(crypto));
        when(cryptoProcessor.getNormalizedRangeForCryptos(List.of(crypto), true)).thenReturn(List.of(expectedNormalizedRange,
                expectedNormalizedRange2));

        // when
        List<CryptoNormalizedRange> normalizedRange = cryptoStatisticService.getNormalizedRange(startDateTime, endDateTime, true);

        // then
        assertEquals(2, normalizedRange.size());
        assertEquals(expectedNormalizedRange, normalizedRange.get(0));
        assertEquals(expectedNormalizedRange2, normalizedRange.get(1));
        verify(cryptoService).getAllInPeriod(startDateTime, endDateTime);
        verify(cryptoProcessor).getNormalizedRangeForCryptos(List.of(crypto), true);
    }

    @Test
    public void test_GetMaxNormalizedRange() {
        // given
        Crypto crypto = new Crypto();
        crypto.setName("BTC");
        crypto.setPrice(100);
        LocalDate date = LocalDate.parse("2021-01-01", DATE_FORMATTER);
        CryptoNormalizedRange expectedNormalizedRange = new CryptoNormalizedRange("BTC", 2.0);

        when(cryptoService.getAllInDay(date.format(DATE_FORMATTER))).thenReturn(List.of(crypto));
        when(cryptoProcessor.getMaxNormalizedRange(List.of(crypto))).thenReturn(expectedNormalizedRange);

        // when
        CryptoNormalizedRange result = cryptoStatisticService.getMaxNormalizedRange(date.format(DATE_FORMATTER));

        // then
        assertEquals(expectedNormalizedRange, result);
        verify(cryptoService).getAllInDay(date.format(DATE_FORMATTER));
        verify(cryptoProcessor).getMaxNormalizedRange(List.of(crypto));
    }

    private CryptoMetrics getExpectedCryptoMetric() {
        CryptoMetrics cryptoMetrics = new CryptoMetrics();
        cryptoMetrics.setName("BTC");
        cryptoMetrics.setLowestPrice(10.0);
        cryptoMetrics.setHighestPrice(100.0);
        cryptoMetrics.setNewestPrice(12.0);
        cryptoMetrics.setOldestPrice(11.0);
        return cryptoMetrics;
    }
}
