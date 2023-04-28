package com.my.project.cryptoservice.dto;

import lombok.Data;

@Data
public class CryptoMetrics {
    private String name;
    private double highestPrice;
    private double lowestPrice;
    private double oldestPrice;
    private double newestPrice;
}
