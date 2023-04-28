package com.my.project.cryptoservice.controller;

import com.my.project.cryptoservice.entity.Crypto;
import com.my.project.cryptoservice.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crypto")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping
    public List<Crypto> getAllCryptos(@RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate) {
        // give me an example of request that will be handled by this controller in the following format "yyyy-MM-dd HH:mm:ss"
        // http://localhost:8080/crypto/api/v1?startDate=2021-01-01%2000:00:00&endDate=2021-01-02%2000:00:00
        // http://localhost:8080/crypto/api/v1?startDate=2021-01-01&endDate=2021-01-02
        if (startDate != null && endDate != null) {
            return cryptoService.getAllInPeriod(startDate, endDate);
        }
        return cryptoService.getAll();
    }
}
