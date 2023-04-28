package com.my.project.cryptoservice.repository;

import com.my.project.cryptoservice.entity.Crypto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CryptoRepository extends CrudRepository<Crypto, Long> {
    List<Crypto> findAll();
    List<Crypto> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Crypto> findByNameAndDateBetween(String name, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
