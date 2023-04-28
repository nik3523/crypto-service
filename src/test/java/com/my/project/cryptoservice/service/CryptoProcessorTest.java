package com.my.project.cryptoservice.service;

import com.my.project.cryptoservice.dto.CryptoMetrics;
import com.my.project.cryptoservice.dto.CryptoNormalizedRange;
import com.my.project.cryptoservice.entity.Crypto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CryptoProcessorTest {

    private final CryptoProcessor cryptoProcessor = new CryptoProcessor();

    @Test
    public void test_GetMetricsForCrypto() {
        //given
        String cryptoName = "BTC";
        LocalDateTime today = LocalDateTime.now();
        List<Crypto> cryptos = getTestCryptos(cryptoName, today, 100);

        //when
        CryptoMetrics result = cryptoProcessor.getMetricsForCrypto(cryptoName, cryptos);

        // then
        assertEquals(cryptoName, result.getName());
        assertEquals(100, result.getLowestPrice());
        assertEquals(300, result.getHighestPrice());
        assertEquals(300, result.getOldestPrice());
        assertEquals(100, result.getNewestPrice());
    }

    @Test
    public void test_GetMetricsForCryptos() {
        // given
        LocalDateTime today = LocalDateTime.now();
        List<Crypto> cryptos = getTestCryptos("BTC", today, 100);
        cryptos.addAll(getTestCryptos("ETH", today, 200));

        // when
        List<CryptoMetrics> result = cryptoProcessor.getMetricsForCryptos(cryptos);

        // then
        assertEquals(2, result.size());
        assertEquals("BTC", result.get(0).getName());
        assertEquals(100, result.get(0).getLowestPrice());
        assertEquals(300, result.get(0).getHighestPrice());
        assertEquals(300, result.get(0).getOldestPrice());
        assertEquals(100, result.get(0).getNewestPrice());
        assertEquals("ETH", result.get(1).getName());
        assertEquals(200, result.get(1).getLowestPrice());
        assertEquals(400, result.get(1).getHighestPrice());
        assertEquals(400, result.get(1).getOldestPrice());
        assertEquals(200, result.get(1).getNewestPrice());
    }

    @Test
    public void test_GetNormalizedRangeForCrypto() {
        // given
        String cryptoName = "BTC";
        LocalDateTime today = LocalDateTime.now();
        List<Crypto> cryptos = getTestCryptos(cryptoName, today, 100);

        //when
        CryptoNormalizedRange result = cryptoProcessor.getNormalizedRangeForCrypto(cryptoName, cryptos);

        //then
        assertEquals(cryptoName, result.getName());
        assertEquals(2, result.getNormalizedRange());
    }

    @Test
    public void test_GetMaxNormalizedRange() {
        //given
        String expectedCryptoName = "BTC";
        LocalDateTime today = LocalDateTime.now();
        List<Crypto> cryptos = getTestCryptos(expectedCryptoName, today, 100);
        cryptos.addAll(getTestCryptos("ETH", today, 200));

        //when
        CryptoNormalizedRange result = cryptoProcessor.getMaxNormalizedRange(cryptos);

        //then
        assertEquals(expectedCryptoName, result.getName());
        // 300 - 100 = 200 / 100 = 2
        assertEquals(2, result.getNormalizedRange());
    }

    @Test
    public void test_getNormalizedRangeForCryptosInAscendingOrder() {
        //given
        LocalDateTime today = LocalDateTime.now();
        List<Crypto> cryptos = getTestCryptos("BTC", today, 100);
        cryptos.addAll(getTestCryptos("ETH", today, 200));

        //when
        List<CryptoNormalizedRange> result = cryptoProcessor.getNormalizedRangeForCryptos(cryptos, true);

        //then
        assertEquals(2, result.size());
        assertEquals("ETH", result.get(0).getName());
        assertEquals("BTC", result.get(1).getName());
        // 400 - 200 = 200 / 200 = 1
        assertEquals(1, result.get(0).getNormalizedRange());
        // 300 - 100 = 200 / 100 = 2
        assertEquals(2, result.get(1).getNormalizedRange());
    }

    @Test
    public void test_test_getNormalizedRangeForCryptosInDescendingOrder() {
        //given
        LocalDateTime today = LocalDateTime.now();
        List<Crypto> cryptos = getTestCryptos("BTC", today, 100);
        cryptos.addAll(getTestCryptos("ETH", today, 200));

        //when
        List<CryptoNormalizedRange> result = cryptoProcessor.getNormalizedRangeForCryptos(cryptos, false);

        //then
        assertEquals(2, result.size());
        assertEquals("BTC", result.get(0).getName());
        assertEquals("ETH", result.get(1).getName());
        // 300 - 100 = 200 / 100 = 2
        assertEquals(2, result.get(0).getNormalizedRange());
        // 400 - 200 = 200 / 200 = 1
        assertEquals(1, result.get(1).getNormalizedRange());
    }

    private static List<Crypto> getTestCryptos(String cryptoName, LocalDateTime today, int initialPrice) {
        List<Crypto> cryptos = new ArrayList<>();
        Crypto crypto1 = new Crypto();
        crypto1.setPrice(initialPrice);
        crypto1.setDate(today);
        crypto1.setName(cryptoName);
        Crypto crypto2 = new Crypto();
        crypto2.setPrice(initialPrice + 100);
        crypto2.setDate(today.minusDays(1));
        crypto2.setName(cryptoName);
        Crypto crypto3 = new Crypto();
        crypto3.setPrice(initialPrice + 200);
        crypto3.setDate(today.minusDays(2));
        crypto3.setName(cryptoName);
        cryptos.add(crypto1);
        cryptos.add(crypto2);
        cryptos.add(crypto3);
        return cryptos;
    }
}
