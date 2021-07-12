package com.example.alfa.service;

import com.example.alfa.feignclient.CurrencyClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest
class GetPreviousDateTest {
    private CurrencyAnalyzer analyzer = mock(CurrencyAnalyzer.class);
    private CurrencyClient client = mock(CurrencyClient.class);
    private CurrencyRateService crs;

    @BeforeEach
    void init() {
        crs = new CurrencyRateService(analyzer, client);
    }

    @Test
    void getPrevDateFromTimestamp_expectTrue1() {
        Assertions.assertEquals("2021-06-23", crs.getPrevDateFromTimestamp(1624525200));
    }

    @Test
    void getPrevDateFromTimestamp_expectTrue2() {
        Assertions.assertEquals("2015-12-11", crs.getPrevDateFromTimestamp(1449877801));
    }

    @Test
    void getPrevDateFromTimestamp_expectTrue3() {
        Assertions.assertEquals("1998-12-31", crs.getPrevDateFromTimestamp(915177407));
    }

    @Test
    void getPrevDateFromTimestamp_expectTrue4() {
        Assertions.assertEquals("1969-12-31", crs.getPrevDateFromTimestamp(-100));
    }

    @Test
    void getPrevDateFromTimestamp_expectTrue5() {
        Assertions.assertEquals("1969-12-31", crs.getPrevDateFromTimestamp(9_223_372_036_854_775_807L));
    }

    @Test
    void getPrevDateFromTimestamp_expectTrue6() {
        Assertions.assertEquals("1969-12-31", crs.getPrevDateFromTimestamp(0));
    }

}
