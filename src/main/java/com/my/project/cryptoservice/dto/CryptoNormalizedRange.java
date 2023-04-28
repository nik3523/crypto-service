package com.my.project.cryptoservice.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CryptoNormalizedRange {
    private String name;
    private double normalizedRange;
}
