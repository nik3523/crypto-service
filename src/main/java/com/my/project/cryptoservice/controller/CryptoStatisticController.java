package com.my.project.cryptoservice.controller;


import com.my.project.cryptoservice.dto.CryptoMetrics;
import com.my.project.cryptoservice.dto.CryptoNormalizedRange;
import com.my.project.cryptoservice.service.CryptoStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic")
public class CryptoStatisticController {

    @Autowired
    private CryptoStatisticService cryptoStatisticService;

    // give me an example of request that will be handled by this controller in the following format "yyyy-MM-dd"
    // http://localhost:8080/api/v1/statistic?startDate=2021-01-01&endDate=2021-01-02
    @GetMapping
    public List<CryptoMetrics> getStatistic(@RequestParam(defaultValue = "2022-01-01") String startDate,
                                            @RequestParam(defaultValue = "2022-02-01") String endDate,
                                            @RequestParam(required = false) String name) {
        if (StringUtils.hasText(name)) {
            return cryptoStatisticService.getMetricsForCrypto(name, startDate, endDate);
        }
        return cryptoStatisticService.getMetricsForAll(startDate, endDate);
    }

    // get me an example of request that will be handled by this controller in the following format "yyyy-MM-dd"
    // http://localhost:8080/api/v1/statistic/normalized-range?startDate=2021-01-01&endDate=2021-01-02&sort=ASC
    @GetMapping("/normalized-range")
    public List<CryptoNormalizedRange> getCryptoNormalizedRanges(@RequestParam(defaultValue = "2022-01-01") String startDate,
                                                                 @RequestParam(defaultValue = "2022-02-01") String endDate,
                                                                 @RequestParam(defaultValue = "DESC") String sort) {
        if ("DESC".equals(sort)) {
            return cryptoStatisticService.getNormalizedRange(startDate, endDate, false);
        }
        return cryptoStatisticService.getNormalizedRange(startDate, endDate, true);
    }

    // get me an example of request that will be handled by this controller in the following format "yyyy-MM-dd"
    // http://localhost:8080/api/v1/statistic/normalized-range/max?date=2022-01-01
    @GetMapping("/normalized-range/max")
    public CryptoNormalizedRange getMaxNormalizedRange(@RequestParam(defaultValue = "2022-01-01") String date) {
        return cryptoStatisticService.getMaxNormalizedRange(date);
    }
}
