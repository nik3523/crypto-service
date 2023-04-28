package com.my.project.cryptoservice.controller;

import com.my.project.cryptoservice.dto.CryptoMetrics;
import com.my.project.cryptoservice.dto.CryptoNormalizedRange;
import com.my.project.cryptoservice.service.CryptoStatisticService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CryptoStatisticControllerTest {

    @InjectMocks
    private CryptoStatisticController cryptoStatisticController;

    @Mock
    private CryptoStatisticService cryptoStatisticService;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(cryptoStatisticService);
    }

    @Test
    public void test_GetStatisticsForAllCryptos() {
        // given
        String startDate = "2021-01-01 00:00:00";
        String endDate = "2021-01-02 00:00:00";

        when(cryptoStatisticService.getMetricsForAll(startDate, endDate)).thenReturn(List.of(new CryptoMetrics()));

        // when
        List<CryptoMetrics> cryptos = cryptoStatisticController.getStatistic(startDate, endDate, null);

        // then
        assertEquals(1, cryptos.size());
        verify(cryptoStatisticService).getMetricsForAll(startDate, endDate);
    }

    @Test
    public void test_GetStatisticForParticularCrypto() {
        // given
        String startDate = "2021-01-01 00:00:00";
        String endDate = "2021-01-02 00:00:00";
        String cryptoName = "BTC";

        when(cryptoStatisticService.getMetricsForCrypto(cryptoName, startDate, endDate)).thenReturn(List.of(new CryptoMetrics()));

        // when
        List<CryptoMetrics> cryptos = cryptoStatisticController.getStatistic(startDate, endDate, cryptoName);

        // then
        assertEquals(1, cryptos.size());
        verify(cryptoStatisticService).getMetricsForCrypto(cryptoName, startDate, endDate);
    }

    @Test
    public void test_GetCryptoNormalizedRangesWithAscSort() {
        // given
        String startDate = "2021-01-01 00:00:00";
        String endDate = "2021-01-02 00:00:00";
        String cryptoName = "BTC";
        String sortDirection = "asc";

        when(cryptoStatisticService.getNormalizedRange(startDate, endDate, true)).thenReturn(List.of(new CryptoNormalizedRange(cryptoName, 1.0)));

        // when
        List<CryptoNormalizedRange> cryptos = cryptoStatisticController.getCryptoNormalizedRanges(startDate, endDate, sortDirection);

        // then
        assertEquals(1, cryptos.size());
        verify(cryptoStatisticService).getNormalizedRange(startDate, endDate, true);
    }

    @Test
    public void test_GetCryptoNormalizedRangesWithDescSort() {
        // given
        String startDate = "2021-01-01 00:00:00";
        String endDate = "2021-01-02 00:00:00";
        String cryptoName = "BTC";
        String sortDirection = "desc";

        when(cryptoStatisticService.getNormalizedRange(startDate, endDate, false)).thenReturn(List.of(new CryptoNormalizedRange(cryptoName, 1.0)));

        // when
        List<CryptoNormalizedRange> cryptos = cryptoStatisticController.getCryptoNormalizedRanges(startDate, endDate, sortDirection);

        // then
        assertEquals(1, cryptos.size());
        verify(cryptoStatisticService).getNormalizedRange(startDate, endDate, false);
    }

    @Test
    public void test_GetMaxNormalizedRange() {
        // given
        String date = "2021-01-01";

        when(cryptoStatisticService.getMaxNormalizedRange(date)).thenReturn(new CryptoNormalizedRange("BTC", 1.0));

        // when
        CryptoNormalizedRange crypto = cryptoStatisticController.getMaxNormalizedRange(date);

        // then
        assertEquals("BTC", crypto.getName());
        assertEquals(1.0, crypto.getNormalizedRange());
        verify(cryptoStatisticService).getMaxNormalizedRange(date);
    }
}
