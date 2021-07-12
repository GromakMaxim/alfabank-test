package com.example.alfa.service;

import com.example.alfa.feignclient.CurrencyClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest
class CrossCurrencyRateTest {
    private CurrencyAnalyzer analyzer = mock(CurrencyAnalyzer.class);
    private CurrencyClient client = mock(CurrencyClient.class);
    private CurrencyRateService crs;

    @BeforeEach
    void init() {
        crs = new CurrencyRateService(analyzer, client);
    }

    @Test
    void calculateCrossCurrencyRate_expectTrue1() {
        var result = crs.calculateCrossCurrencyRate(74.41, 81.69);
        result = (double) Math.round(result * 100) / 100;
        Assertions.assertEquals(0.91, result);
    }

    @Test
    void calculateCrossCurrencyRate_expectTrue2() {
        var result = crs.calculateCrossCurrencyRate(74.41, 103.24);
        result = (double) Math.round(result * 100) / 100;
        Assertions.assertEquals(0.72, result);
    }

    @Test
    void calculateCrossCurrencyRate_expectTrue3() {
        CurrencyRateService crs = new CurrencyRateService(analyzer, client);
        var result = crs.calculateCrossCurrencyRate(74.41, 103.24);
        result = (double) Math.round(result * 100) / 100;
        Assertions.assertEquals(0.72, result);
    }

    @Test()
    void calculateCrossCurrencyRate_expectArithmeticExc1() {
        var crs = new CurrencyRateService(analyzer, client);
        Assertions.assertThrows(
                ArithmeticException.class,
                () -> crs.calculateCrossCurrencyRate(0, 0));
    }

    @Test()
    void calculateCrossCurrencyRate_expectArithmeticExc2() {
        var crs = new CurrencyRateService(analyzer, client);
        Assertions.assertThrows(
                ArithmeticException.class,
                () -> crs.calculateCrossCurrencyRate(0, 124));
    }

    @Test()
    void calculateCrossCurrencyRate_expectArithmeticExc3() {
        var crs = new CurrencyRateService(analyzer, client);
        Assertions.assertThrows(
                ArithmeticException.class,
                () -> crs.calculateCrossCurrencyRate(67.0, 0));
    }

    @Test()
    void calculateCrossCurrencyRate_expectIllegalArgumentExc1() {
        var crs = new CurrencyRateService(analyzer, client);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> crs.calculateCrossCurrencyRate(-1, -50));
    }

    @Test()
    void calculateCrossCurrencyRate_expectIllegalArgumentExc2() {
        var crs = new CurrencyRateService(analyzer, client);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> crs.calculateCrossCurrencyRate(-1.98, 0));
    }

    @Test()
    void calculateCrossCurrencyRate_expectIllegalArgumentExc3() {
        var crs = new CurrencyRateService(analyzer, client);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> crs.calculateCrossCurrencyRate(0, -50.56));
    }

    @Test()
    void calculateCrossCurrencyRate_expectIllegalArgumentExc4() {
        var crs = new CurrencyRateService(analyzer, client);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> crs.calculateCrossCurrencyRate(56.98, -50.56));
    }

    @Test()
    void calculateCrossCurrencyRate_expectIllegalArgumentExc5() {
        var crs = new CurrencyRateService(analyzer, client);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> crs.calculateCrossCurrencyRate(-56.98, 50.56));
    }
}
