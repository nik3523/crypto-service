package com.my.project.cryptoservice.service;

import com.my.project.cryptoservice.entity.Crypto;
import com.my.project.cryptoservice.repository.CryptoRepository;
import com.my.project.cryptoservice.validation.CryptoValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.my.project.cryptoservice.util.DateTimeUtils.toEndOfDay;
import static com.my.project.cryptoservice.util.DateTimeUtils.toLocalDateTime;
import static com.my.project.cryptoservice.util.DateTimeUtils.toStartOfDay;

@Service
@Log4j2
public class CryptoService {
    public CryptoService(CryptoRepository cryptoRepository, CryptoValidator cryptoValidator) {
        this.cryptoRepository = cryptoRepository;
        this.cryptoValidator = cryptoValidator;
    }

    private final CryptoRepository cryptoRepository;
    private final CryptoValidator cryptoValidator;

    public List<Crypto> getAll() {
        List<Crypto> cryptos = cryptoRepository.findAll();
        log.debug("Found {} cryptos", cryptos.size());
        return cryptos;
    }

    public List<Crypto> saveAll(List<Crypto> cryptos) {
        List<Crypto> savedCryptos = (List<Crypto>) cryptoRepository.saveAll(cryptos);
        log.debug("Saved {} cryptos", savedCryptos.size());
        return  savedCryptos;
    }

    public List<Crypto> getAllInPeriod(String startDate, String endDate) {
        LocalDateTime startDateTime = toLocalDateTime(startDate);
        LocalDateTime endDateTime = toLocalDateTime(endDate);
        cryptoValidator.validatePeriod(startDateTime, endDateTime);
        List<Crypto> cryptos = cryptoRepository.findByDateBetween(startDateTime, endDateTime);
        log.debug("Found {} cryptos by period {} - {}", cryptos.size(), startDate, endDate);
        return cryptos;
    }

    public List<Crypto> getByNameInPeriod(String name, String startDate, String endDate) {
        cryptoValidator.validateCryptoSupporting(name);
        cryptoValidator.validatePeriod(toLocalDateTime(startDate), toLocalDateTime(endDate));
        List<Crypto> cryptos = cryptoRepository.findByNameAndDateBetween(name, toLocalDateTime(startDate), toLocalDateTime(endDate));
        log.debug("Found {} cryptos by name {} and period {} - {}", cryptos.size(), name, startDate, endDate);
        return cryptos;
    }

    public List<Crypto> getAllInDay(String date) {
        List<Crypto> cryptos = cryptoRepository.findByDateBetween(toStartOfDay(date), toEndOfDay(date));
        log.debug("Found {} cryptos in a day: {}", cryptos.size(), date);
        return cryptos;
    }
}
