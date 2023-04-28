package com.my.project.cryptoservice.service;

import com.my.project.cryptoservice.dto.CryptoMetrics;
import com.my.project.cryptoservice.dto.CryptoNormalizedRange;
import com.my.project.cryptoservice.entity.Crypto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.singletonList;

@Service
@Log4j2
public class CryptoStatisticService {

    @Autowired
    public CryptoStatisticService(CryptoService cryptoService, CryptoProcessor cryptoProcessor) {
        this.cryptoService = cryptoService;
        this.cryptoProcessor = cryptoProcessor;
    }

    private final CryptoService cryptoService;
    private final CryptoProcessor cryptoProcessor;

    public List<CryptoMetrics> getMetricsForAll(String startDate, String endDate) {
        List<Crypto> metricsInPeriod = cryptoService.getAllInPeriod(startDate, endDate);
        return cryptoProcessor.getMetricsForCryptos(metricsInPeriod);
    }

    public List<CryptoMetrics> getMetricsForCrypto(String name, String startDate, String endDate) {
        List<Crypto> metricsInPeriod = cryptoService.getByNameInPeriod(name, startDate, endDate);
        return singletonList(cryptoProcessor.getMetricsForCrypto(name, metricsInPeriod));
    }

    public List<CryptoNormalizedRange> getNormalizedRange(String startDate, String endDate, boolean isAscending) {
        List<Crypto> metricsInPeriod = cryptoService.getAllInPeriod(startDate, endDate);
        return cryptoProcessor.getNormalizedRangeForCryptos(metricsInPeriod, isAscending);
    }

    public CryptoNormalizedRange getMaxNormalizedRange(String date) {
        List<Crypto> cryptos = cryptoService.getAllInDay(date);
        return cryptoProcessor.getMaxNormalizedRange(cryptos);
    }
}
