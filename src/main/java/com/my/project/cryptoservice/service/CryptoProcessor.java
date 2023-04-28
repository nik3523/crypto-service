package com.my.project.cryptoservice.service;

import com.my.project.cryptoservice.dto.CryptoMetrics;
import com.my.project.cryptoservice.dto.CryptoNormalizedRange;
import com.my.project.cryptoservice.entity.Crypto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Log4j2
public class CryptoProcessor {

    private static final Crypto EMPTY_CRYPTO = new Crypto();

    /**
     * Combining crypto's metrics by name
     * @param cryptoName {@link String} - crypto's name
     * @param cryptos    {@link List<Crypto>} - list of cryptos to process
     * @return {@link CryptoMetrics} - all metrics for crypto
     */
    public CryptoMetrics getMetricsForCrypto(String cryptoName, List<Crypto> cryptos) {
        CryptoMetrics CryptoMetrics = new CryptoMetrics();
        CryptoMetrics.setName(cryptoName);
        CryptoMetrics.setLowestPrice(getTheLowestPrice(cryptos));
        CryptoMetrics.setHighestPrice(getTheHighestPrice(cryptos));
        CryptoMetrics.setOldestPrice(getTheOldestPrice(cryptos));
        CryptoMetrics.setNewestPrice(getTheNewestPrice(cryptos));
        return CryptoMetrics;
    }

    /**
     * Calculating the normalized range for crypto
     * Formula: (the highest price - the lowest price) / the lowest price
     * @param name    {@link String} - crypto's name
     * @param cryptos {@link List<Crypto>} - list of cryptos to process
     * @return {@link CryptoNormalizedRange} - normalized range for crypto
     */
    public CryptoNormalizedRange getNormalizedRangeForCrypto(String name, List<Crypto> cryptos) {
        double theLowestPrice = getTheLowestPrice(cryptos);
        double theHighestPrice = getTheHighestPrice(cryptos);
        log.debug("Calculating normalized range for crypto: {}, the highest price = {}, the lowest price = {}", name, theHighestPrice, theLowestPrice);
        return new CryptoNormalizedRange(name, (theHighestPrice - theLowestPrice) / theLowestPrice);
    }

    /**
     * Calculating the normalized range for list of cryptos
     * @param cryptos     {@link List<Crypto>} - list of cryptos to process
     * @param isAscending {@link boolean} - sort order
     * @return {@link List<CryptoNormalizedRange>} - normalized range for list of cryptos
     */
    public List<CryptoNormalizedRange> getNormalizedRangeForCryptos(List<Crypto> cryptos, boolean isAscending) {
        List<CryptoNormalizedRange> result = getNormalizedRangeForCryptos(cryptos);
        if (isAscending) {
            result.sort(comparing(CryptoNormalizedRange::getNormalizedRange));
        } else {
            result.sort(comparing(CryptoNormalizedRange::getNormalizedRange).reversed());
        }
        return result;
    }

    /**
     * Calculating the normalized range for list of cryptos
     * @param cryptos {@link List<Crypto>} - list of cryptos to process
     * @return {@link List<CryptoNormalizedRange>} - normalized range for list of cryptos
     */
    public List<CryptoMetrics> getMetricsForCryptos(List<Crypto> cryptos) {
        final List<CryptoMetrics> CryptoMetrics = new ArrayList<>();
        combineCryptosByName(cryptos).forEach((k, v) -> CryptoMetrics.add(getMetricsForCrypto(k, v)));
        return CryptoMetrics;
    }

    /**
     * Looking for the crypto with the highest normalized range from the list of cryptos
     * @param cryptos {@link List<Crypto>} - list of cryptos to process
     * @return {@link CryptoNormalizedRange} - crypto with the highest normalized range
     */
    public CryptoNormalizedRange getMaxNormalizedRange(List<Crypto> cryptos) {
        return getNormalizedRangeForCryptos(cryptos)
                .stream()
                .max(comparing(CryptoNormalizedRange::getNormalizedRange))
                .orElse(null);
    }

    /**
     * Looking for a Crypto with the highest price from the list of cryptos
     * @param cryptos {@link List<Crypto>} - list of cryptos to process
     * @return {@link Crypto} - crypto with the highest price
     */
    private double getTheHighestPrice(List<Crypto> cryptos) {
        return cryptos.stream()
                .mapToDouble(Crypto::getPrice)
                .max()
                .orElse(0);
    }

    /**
     * Looking for a Crypto with the lowest price from the list of cryptos
     * @param cryptos {@link List<Crypto>} - list of cryptos to process
     * @return {@link Crypto} - crypto with the lowest price
     */
    private double getTheLowestPrice(List<Crypto> cryptos) {
        return cryptos.stream()
                .mapToDouble(Crypto::getPrice)
                .min()
                .orElse(0);
    }

    /**
     * Looking for a Crypto with the oldest price from the list of cryptos
     * @param cryptos {@link List<Crypto>} - list of cryptos to process
     * @return {@link Crypto} - crypto with the oldest price
     */
    private double getTheOldestPrice(List<Crypto> cryptos) {
        return cryptos.stream()
                .min(comparing(Crypto::getDate))
                .orElse(EMPTY_CRYPTO)
                .getPrice();
    }

    /**
     * Looking for a Crypto with the newest price from the list of cryptos
     * @param cryptos {@link List<Crypto>} - list of cryptos to process
     * @return {@link Crypto} - crypto with the newest price
     */
    private double getTheNewestPrice(List<Crypto> cryptos) {
        return cryptos.stream()
                .max(comparing(Crypto::getDate))
                .orElse(EMPTY_CRYPTO)
                .getPrice();
    }

    /**
     * Calculating the normalized range for list of different cryptos
     * @param cryptos {@link List<Crypto>} - list of cryptos to process
     * @return {@link List<CryptoNormalizedRange>} - normalized range for different cryptos
     */
    private List<CryptoNormalizedRange> getNormalizedRangeForCryptos(List<Crypto> cryptos) {
        List<CryptoNormalizedRange> result = new ArrayList<>();
        combineCryptosByName(cryptos).forEach((k, v) -> result.add(getNormalizedRangeForCrypto(k, v)));
        return result;
    }

    /**
     * Combining list of cryptos by name to map
     * Key - crypto's name
     * Value - list of cryptos with the same name
     * @param cryptos {@link List<Crypto>} - list of cryptos to process
     * @return {@link Map<String, List<Crypto>>} - map of cryptos by name
     */
    private Map<String, List<Crypto>> combineCryptosByName(List<Crypto> cryptos) {
        return cryptos.stream()
                .collect(Collectors.groupingBy(Crypto::getName));
    }
}
