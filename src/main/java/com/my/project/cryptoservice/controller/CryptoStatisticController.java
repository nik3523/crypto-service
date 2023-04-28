package com.my.project.cryptoservice.controller;


import com.my.project.cryptoservice.dto.CryptoMetrics;
import com.my.project.cryptoservice.dto.CryptoNormalizedRange;
import com.my.project.cryptoservice.service.CryptoStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Crypto statistic controller", description = "Operations with crypto statistic")
@RestController
@RequestMapping("/api/v1/statistic")
public class CryptoStatisticController {
    private final CryptoStatisticService cryptoStatisticService;

    @Autowired
    public CryptoStatisticController(CryptoStatisticService cryptoStatisticService) {
        this.cryptoStatisticService = cryptoStatisticService;
    }

    @Operation(summary = "Get crypto statistic", description = "<p>Get crypto statistic from start date to end date.</p>" +
            "<p>If start date and end date are not provided, default values will be applied (<b>In current app the period where data exists</b>).</p>" +
            "<p>If start date and end date are provided, all crypto statistic in period will be returned.</p>" +
            "<p>If name is provided, all crypto statistic for this crypto will be returned.</p>" +
            "<p>If name is not provided, all crypto statistic will be returned.</p>" +
            "<p>Date format: <b>yyyy-MM-dd HH:mm:ss</b></p>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved crypto statistic"),
            @ApiResponse(responseCode = "400", description = "Bad request. For example: invalid date format or requested Crypto is not in available list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<CryptoMetrics> getStatistic(@Parameter(description = "Start date of requested period")
                                                @RequestParam(defaultValue = "2022-01-01") String startDate,
                                            @Parameter(description = "End date of requested period")
                                                @RequestParam(defaultValue = "2022-02-01") String endDate,
                                            @Parameter(description = "Name of crypto")
                                                @RequestParam(required = false) String name) {
        if (StringUtils.hasText(name)) {
            return cryptoStatisticService.getMetricsForCrypto(name, startDate, endDate);
        }
        return cryptoStatisticService.getMetricsForAll(startDate, endDate);
    }

    @Operation(summary = "Get crypto normalized range", description = "<p>Get crypto normalized range from start date to end date.</p>" +
            "<p>If start date and end date are not provided, default values will be applied (<b>In current app the period where data exists</b>).</p>" +
            "<p>If start date and end date are provided, all crypto normalized range in period will be returned.</p>" +
            "<p>If sort is not provided, all data will be returned in descending order.</p>" +
            "<p>If sort is provided, all data will be returned in requested order.</p>" +
            "<p>Date format: <b>yyyy-MM-dd HH:mm:ss</b></p>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved crypto normalized ranges"),
            @ApiResponse(responseCode = "400", description = "Bad request. For example: invalid date format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/normalized-range")
    public List<CryptoNormalizedRange> getCryptoNormalizedRanges(@Parameter(description = "Start date of requested period")
                                                                     @RequestParam(defaultValue = "2022-01-01") String startDate,
                                                                 @Parameter(description = "End date of requested period")
                                                                    @RequestParam(defaultValue = "2022-02-01") String endDate,
                                                                 @Parameter(description = "Sort direction")
                                                                    @RequestParam(defaultValue = "DESC") String sort) {
        if ("ASC".equalsIgnoreCase(sort)) {
            return cryptoStatisticService.getNormalizedRange(startDate, endDate, true);
        }
        return cryptoStatisticService.getNormalizedRange(startDate, endDate, false);
    }

    @Operation(summary = "Get crypto with max normalized range value", description = "<p>Get crypto with max normalized range value in a day.</p>" +
            "<p>If date is not provided, default value will be applied (<b>In current app the day where data exists</b>).</p>" +
            "<p>If date is provided, all crypto in a day will be processed.</p>" +
            "<p>Date format: <b>yyyy-MM-dd</b></p>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved crypto with max normalized range value"),
            @ApiResponse(responseCode = "400", description = "Bad request. For example: invalid date format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/normalized-range/max")
    public CryptoNormalizedRange getMaxNormalizedRange(@RequestParam(defaultValue = "2022-01-01") String date) {
        return cryptoStatisticService.getMaxNormalizedRange(date);
    }
}
