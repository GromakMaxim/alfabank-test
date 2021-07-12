package com.example.alfa.service;

import com.example.alfa.dto.api.response.currency.CurrencyResponse;
import com.example.alfa.feignclient.CurrencyClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetCurrentRateTest {
    private CurrencyAnalyzer analyzer = mock(CurrencyAnalyzer.class);
    private CurrencyClient client = mock(CurrencyClient.class);
    private CurrencyRateService crs;

    @BeforeEach
    void init() {
        crs = new CurrencyRateService(analyzer, client);
        crs.setDesiredCurrency("RUB");
    }

    private double round(double value) {
        return (double) Math.round(value * 100) / 100;
    }

    private HashMap<String, Double> createTestMap(String name, Double cur) {
        var map = new HashMap<String, Double>();
        map.put(name, cur);
        map.put("RUB", 74.41);
        return map;
    }

    @Test
    void getCurrentRate_expectTrue1() {
        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(createTestMap("AFN", 81.69)));

        var actual = crs.getCurrentRate("AFN");
        var expected = 0.91;
        Assertions.assertEquals(round(actual), expected);
    }

    @Test
    void getCurrentRate_expectTrue2() {
        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(createTestMap("ALL", 103.24)));

        var actual = crs.getCurrentRate("ALL");
        var expected = 0.72;
        Assertions.assertEquals(round(actual), expected);
    }

    @Test
    void getCurrentRate_expectTrue3() {
        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(createTestMap("AMD", 484.9)));

        var actual = crs.getCurrentRate("AMD");
        var expected = 0.15;
        Assertions.assertEquals(round(actual), expected);
    }

    @Test
    void getCurrentRate_expectArithmeticExc1() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", 0.0);
        map.put("RUB", 0.0);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));

        Assertions.assertThrows(ArithmeticException.class, () -> crs.getCurrentRate("AMD"));
    }

    @Test
    void getCurrentRate_expectArithmeticExc2() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", 10.5);
        map.put("RUB", 0.0);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));

        Assertions.assertThrows(ArithmeticException.class, () -> crs.getCurrentRate("AMD"));
    }

    @Test
    void getCurrentRate_expectArithmeticExc3() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", 0.0);
        map.put("RUB", 10.6);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));

        Assertions.assertThrows(ArithmeticException.class, () -> crs.getCurrentRate("AMD"));
    }

    @Test
    void getCurrentRate_expectIllegalArgumentExc1() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", -1.0);
        map.put("RUB", -5.6);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));

        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getCurrentRate("AMD"));
    }

    @Test
    void getCurrentRate_expectIllegalArgumentExc2() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", 0.0);
        map.put("RUB", -5.6);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));

        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getCurrentRate("AMD"));
    }

    @Test
    void getCurrentRate_expectIllegalArgumentExc3() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", -9.0);
        map.put("RUB", 0.6);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));

        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getCurrentRate("AMD"));
    }

    @Test
    void getCurrentRate_expectIllegalArgumentExc4() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", 5.0);
        map.put("RUB", -0.6);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));

        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getCurrentRate("AMD"));
    }

    @Test
    void getCurrentRate_expectIllegalArgumentExc5() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", -5.0);
        map.put("RUB", 10.6);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));

        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getCurrentRate("AMD"));
    }

    @Test
    void getCurrentRate_expectIllegalArgumentExc6() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", 5.0);
        map.put("RUB", 10.6);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));
        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getCurrentRate("AAA"));
    }

    @Test
    void getCurrentRate_expectIllegalArgumentExc7() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", 5.0);
        map.put("RUB", 10.6);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));
        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getCurrentRate(""));
    }

    @Test
    void getCurrentRate_expectIllegalArgumentExc8() {
        var map = new HashMap<String, Double>();
        //base is USD
        map.put("AMD", 5.0);
        map.put("RUB", 10.6);

        when(client.getCurrentRate(any())).thenReturn(new CurrencyResponse(map));
        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getCurrentRate(" "));
    }
}
