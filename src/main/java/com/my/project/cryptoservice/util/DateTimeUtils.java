package com.my.project.cryptoservice.util;

import com.my.project.cryptoservice.exception.CryptoValidatorException;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Log4j2
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
        return parseDateTimeString(dateTime);
    }

    public static LocalDateTime toStartOfDay(String dateTime) {
        return LocalDateTime.of(parseDateString(dateTime), LocalTime.MIN);
    }

    public static LocalDateTime toEndOfDay(String dateTime) {
        return LocalDateTime.of(parseDateString(dateTime), LocalTime.MAX);
    }

    private static LocalDate parseDateString(String date) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, getFormatter());
        } catch (Exception e) {
            log.error("Wrong date format: " + date);
            throw new CryptoValidatorException("Wrong date format: " + date);
        }
        return localDate;
    }

    private static LocalDateTime parseDateTimeString(String date) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(date, getFormatter());
        } catch (Exception e) {
            log.error("Wrong date/time format: " + date);
            throw new CryptoValidatorException("Wrong date/time format: " + date);
        }
        return localDateTime;
    }
}
