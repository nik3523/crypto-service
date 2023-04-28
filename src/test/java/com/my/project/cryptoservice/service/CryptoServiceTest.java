package com.my.project.cryptoservice.service;

import com.my.project.cryptoservice.entity.Crypto;
import com.my.project.cryptoservice.repository.CryptoRepository;
import com.my.project.cryptoservice.validation.CryptoValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.my.project.cryptoservice.util.DateTimeUtils.getFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CryptoServiceTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = getFormatter();

    @InjectMocks
    private CryptoService cryptoService;

    @Mock
    private CryptoRepository cryptoRepository;

    @Mock
    private CryptoValidator cryptoValidator;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(cryptoRepository);
    }

    @Test
    public void test_GetAll() {
        // given
        Crypto crypto = new Crypto();
        crypto.setName("BTC");
        crypto.setPrice(100);
        when(cryptoRepository.findAll()).thenReturn(List.of(crypto));

        // when
        List<Crypto> result = cryptoService.getAll();

        // then
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getName());
        assertEquals(100, result.get(0).getPrice());
        verify(cryptoRepository).findAll();
    }

    @Test
    public void test_GetAllInPeriod_Successful() {
        // given
        Crypto crypto = new Crypto();
        crypto.setName("BTC");
        crypto.setPrice(100);
        LocalDateTime localDateTime = LocalDateTime.parse("2021-01-01 00:00:00", DATE_TIME_FORMATTER);
        LocalDateTime localDateTime2 = localDateTime.plusDays(1);

        when(cryptoRepository.findByDateBetween(localDateTime, localDateTime2)).thenReturn(List.of(crypto));

        // when
        List<Crypto> result = cryptoService.getAllInPeriod(localDateTime.format(DATE_TIME_FORMATTER),
                localDateTime2.format(DATE_TIME_FORMATTER));

        // then
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getName());
        assertEquals(100, result.get(0).getPrice());
        verify(cryptoRepository).findByDateBetween(localDateTime, localDateTime2);
        verify(cryptoValidator).validatePeriod(localDateTime, localDateTime2);
    }

    @Test
    public void test_GetByNameInPeriod() {
        // given
        Crypto crypto = new Crypto();
        crypto.setName("BTC");
        crypto.setPrice(100);
        LocalDateTime localDateTime = LocalDateTime.parse("2021-01-01 00:00:00", DATE_TIME_FORMATTER);
        LocalDateTime localDateTime2 = localDateTime.plusDays(1);

        when(cryptoRepository.findByNameAndDateBetween("BTC", localDateTime, localDateTime2)).thenReturn(List.of(crypto));

        // when
        List<Crypto> result = cryptoService.getByNameInPeriod("BTC", localDateTime.format(DATE_TIME_FORMATTER),
                localDateTime2.format(DATE_TIME_FORMATTER));

        // then
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getName());
        assertEquals(100, result.get(0).getPrice());
        verify(cryptoRepository).findByNameAndDateBetween("BTC", localDateTime, localDateTime2);
        verify(cryptoValidator).validatePeriod(localDateTime, localDateTime2);
        verify(cryptoValidator).validateCryptoSupporting("BTC");
    }

    @Test
    public void test_GetAllInDay() {
        // given
        Crypto crypto = new Crypto();
        crypto.setName("BTC");
        crypto.setPrice(100);
        LocalDate localDate = LocalDate.parse("2021-01-01", DATE_TIME_FORMATTER);

        when(cryptoRepository.findByDateBetween(localDate.atStartOfDay(), localDate.atTime(LocalTime.MAX))).thenReturn(List.of(crypto));

        // when
        List<Crypto> result = cryptoService.getAllInDay(localDate.format(DATE_TIME_FORMATTER));

        // then
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getName());
        assertEquals(100, result.get(0).getPrice());
        verify(cryptoRepository).findByDateBetween(localDate.atStartOfDay(), localDate.atTime(LocalTime.MAX));
    }

    @Test
    public void test_SaveAll() {
        // given
        Crypto crypto = new Crypto();
        crypto.setName("BTC");
        crypto.setPrice(100);
        when(cryptoRepository.saveAll(any())).thenReturn(List.of(crypto));

        // when
        List<Crypto> result = cryptoService.saveAll(List.of(crypto));

        // then
        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getName());
        assertEquals(100, result.get(0).getPrice());
        verify(cryptoRepository).saveAll(List.of(crypto));
    }
}
