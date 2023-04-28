package com.my.project.cryptoservice.entity;

import com.my.project.cryptoservice.util.TimestampToLocalDateTimeConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Crypto {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @CsvCustomBindByName(column = "timestamp", converter = TimestampToLocalDateTimeConverter.class)
    private LocalDateTime date;

    @CsvBindByName(column = "symbol")
    private String name;

    @CsvBindByName(column = "price")
    private double price;
}
