package com.my.project.cryptoservice.controller;

import com.my.project.cryptoservice.entity.Crypto;
import com.my.project.cryptoservice.service.CryptoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CryptoControllerTest {

    @InjectMocks
    private CryptoController cryptoController;

    @Mock
    private CryptoService cryptoService;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(cryptoService);
    }

    @Test
    public void testGetAllCryptosWithDates() {
        // given
        String startDate = "2021-01-01 00:00:00";
        String endDate = "2021-01-02 00:00:00";

        when(cryptoService.getAllInPeriod(startDate, endDate)).thenReturn(List.of(new Crypto()));

        // when
        List<Crypto> cryptos = cryptoController.getAllCryptos(startDate, endDate);

        // then
        assertEquals(1, cryptos.size());
        verify(cryptoService).getAllInPeriod(startDate, endDate);
    }

    @Test
    public void testGetAllCryptosWithNulls() {
        // given
        when(cryptoService.getAll()).thenReturn(List.of(new Crypto()));

        // when
        List<Crypto> cryptos = cryptoController.getAllCryptos(null, null);

        // then
        assertEquals(1, cryptos.size());
        verify(cryptoService).getAll();
    }
}
