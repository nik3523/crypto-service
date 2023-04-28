package com.my.project.cryptoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CryptoNormalizedRange {
    private String name;
    private double normalizedRange;
}
