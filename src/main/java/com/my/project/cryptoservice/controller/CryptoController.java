package com.my.project.cryptoservice.controller;

import com.my.project.cryptoservice.entity.Crypto;
import com.my.project.cryptoservice.service.CryptoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.nonNull;

@Tag(name = "Cryptos controller", description = "Operations with crypto history data")
@RestController
@RequestMapping("/api/v1/crypto")
public class CryptoController {

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    private final CryptoService cryptoService;

    @Operation(summary = "Get all crypto history data", description = "<p>Get all crypto history data from start date to end date.</p>" +
            "<p>If start date or end date are not provided, all crypto history data will be returned.</p>" +
            "<p>If start date and end date are provided, all crypto history data in period will be returned.</p>" +
            "<p>Date format: <b>yyyy-MM-dd HH:mm:ss</b></p>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved crypto history data"),
            @ApiResponse(responseCode = "400", description = "Wrong request. Such as wrong format or start date is after end date"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Crypto> getAllCryptos(@Parameter(description = "Start date for requested period")
                                          @RequestParam(required = false) String startDate,
                                      @Parameter(description = "End date for requested period")
                                          @RequestParam(required = false) String endDate) {
        if (nonNull(startDate) && nonNull(endDate)) {
            return cryptoService.getAllInPeriod(startDate, endDate);
        }
        return cryptoService.getAll();
    }
}
