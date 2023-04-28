package com.my.project.cryptoservice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeUtils {

    private static DateTimeFormatter formatter;

    private DateTimeUtils() {
        // private constructor
    }

    public static DateTimeFormatter getFormatter() {
        if (formatter == null) {
            formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd")
                    .optionalStart()
                    .appendPattern(" HH:mm:ss")
                    .optionalEnd()
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();
        }
        return formatter;
    }

    public static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, getFormatter());
    }

    public static LocalDateTime toStartOfDay(String dateTime) {
        return LocalDateTime.of(LocalDate.parse(dateTime, getFormatter()), LocalTime.MIN);
    }

    public static LocalDateTime toEndOfDay(String dateTime) {
        return LocalDateTime.of(LocalDate.parse(dateTime, getFormatter()), LocalTime.MAX);
    }
}
