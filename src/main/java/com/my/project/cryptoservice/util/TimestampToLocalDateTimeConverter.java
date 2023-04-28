package com.my.project.cryptoservice.util;

import com.opencsv.bean.AbstractBeanField;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimestampToLocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {
    @Override
    protected LocalDateTime convert(String value) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(value)), ZoneId.systemDefault());
    }
}
