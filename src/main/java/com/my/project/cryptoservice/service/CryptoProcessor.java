package com.my.project.cryptoservice.service;

import com.my.project.cryptoservice.dto.CryptoMetrics;
import com.my.project.cryptoservice.dto.CryptoNormalizedRange;
import com.my.project.cryptoservice.entity.Crypto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CryptoProcessor {

    private static final Crypto EMPTY_CRYPTO = new Crypto();

    public double getTheHighestPrice(List<Crypto> cryptos) {
        return cryptos.stream()
                .mapToDouble(Crypto::getPrice)
                .max()
                .orElse(0);
    }

    public double getTheLowestPrice(List<Crypto> cryptos) {
        return cryptos.stream()
                .mapToDouble(Crypto::getPrice)
                .min()
                .orElse(0);
    }

    public double getTheOldestPrice(List<Crypto> cryptos) {
        return cryptos.stream()
                .min(Comparator.comparing(Crypto::getDate))
                .orElse(EMPTY_CRYPTO)
                .getPrice();
    }

    public double getTheNewestPrice(List<Crypto> cryptos) {
        return cryptos.stream()
                .max(Comparator.comparing(Crypto::getDate))
                .orElse(EMPTY_CRYPTO)
                .getPrice();
    }


    public CryptoMetrics getMetricsForCrypto(String cryptoName, List<Crypto> cryptos) {
        CryptoMetrics CryptoMetrics = new CryptoMetrics();
        CryptoMetrics.setName(cryptoName);
        CryptoMetrics.setLowestPrice(getTheLowestPrice(cryptos));
        CryptoMetrics.setHighestPrice(getTheHighestPrice(cryptos));
        CryptoMetrics.setOldestPrice(getTheOldestPrice(cryptos));
        CryptoMetrics.setNewestPrice(getTheNewestPrice(cryptos));
        return CryptoMetrics;
    }

    public CryptoNormalizedRange getNormalizedRangeForCrypto(String name, List<Crypto> cryptos) {
        double theLowestPrice = getTheLowestPrice(cryptos);
        double theHighestPrice = getTheHighestPrice(cryptos);
        log.debug("Calculating normalized range for crypto: {}, the highest price = {}, the lowest price = {}", name, theHighestPrice, theLowestPrice);
        return new CryptoNormalizedRange(name, (theHighestPrice - theLowestPrice) / theLowestPrice);
    }

    public List<CryptoNormalizedRange> getNormalizedRangeForCryptos(List<Crypto> cryptos, boolean isAscending) {
        List<CryptoNormalizedRange> result = getNormalizedRangeForCryptos(cryptos);
        if (isAscending) {
            result.sort(Comparator.comparing(CryptoNormalizedRange::getNormalizedRange));
        } else {
            result.sort(Comparator.comparing(CryptoNormalizedRange::getNormalizedRange).reversed());
        }
        return result;
    }

    public List<CryptoMetrics> getMetricsForCryptos(List<Crypto> cryptos) {
        final List<CryptoMetrics> CryptoMetrics = new ArrayList<>();
        combineCryptosByName(cryptos).forEach((k, v) -> CryptoMetrics.add(getMetricsForCrypto(k, v)));
        return CryptoMetrics;
    }
    public CryptoNormalizedRange getMaxNormalizedRange(List<Crypto> cryptos) {
        return getNormalizedRangeForCryptos(cryptos).stream()
                .max(Comparator.comparing(CryptoNormalizedRange::getNormalizedRange))
                .orElse(null);
    }

    private List<CryptoNormalizedRange> getNormalizedRangeForCryptos(List<Crypto> cryptos) {
        List<CryptoNormalizedRange> result = new ArrayList<>();
        combineCryptosByName(cryptos).forEach((k, v) -> result.add(getNormalizedRangeForCrypto(k, v)));
        return result;
    }

    private Map<String, List<Crypto>> combineCryptosByName(List<Crypto> cryptos) {
        return cryptos.stream()
                .collect(Collectors.groupingBy(Crypto::getName));
    }

}
