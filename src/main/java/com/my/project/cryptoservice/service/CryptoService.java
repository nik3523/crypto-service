package com.my.project.cryptoservice.service;

import com.my.project.cryptoservice.entity.Crypto;
import com.my.project.cryptoservice.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.my.project.cryptoservice.util.DateTimeUtils.toLocalDateTime;
import static com.my.project.cryptoservice.util.DateTimeUtils.toEndOfDay;
import static com.my.project.cryptoservice.util.DateTimeUtils.toStartOfDay;

@Service
public class CryptoService {

    @Autowired
    private CryptoRepository cryptoRepository;

    public List<Crypto> getAll() {
        return cryptoRepository.findAll();
    }

    public List<Crypto> saveAll(List<Crypto> cryptos) {
        return (List<Crypto>) cryptoRepository.saveAll(cryptos);
    }

    public List<Crypto> getAllInPeriod(String startDate, String endDate) {
        return cryptoRepository.findByDateBetween(toLocalDateTime(startDate), toLocalDateTime(endDate));
    }

    public List<Crypto> getByNameInPeriod(String name, String startDate, String endDate) {
        return cryptoRepository.findByNameAndDateBetween(name, toLocalDateTime(startDate), toLocalDateTime(endDate));
    }

    public List<Crypto> getAllInDay(String date) {
        return cryptoRepository.findByDateBetween(toStartOfDay(date), toEndOfDay(date));
    }
}
