package com.example.alfa.service;

import com.example.alfa.dto.api.response.currency.CurrencyResponse;
import com.example.alfa.feignclient.CurrencyClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetPreviousRateTest {
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
    void getPreviousRate_expectTrue1() {
        var timestamp = 1624525200L;//2021-06-23
        var time = crs.getPrevDateFromTimestamp(timestamp);

        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 81.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 80.69));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(client.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(client.getYesterdayRate(eq(time), any())).thenReturn(yesterdayCurrencyResponse);
        crs.getCurrentRate("AFN");

        var actual = crs.getPreviousRate("AFN");
        var expected = 0.92;
        Assertions.assertEquals(expected, round(actual));
    }

    @Test
    void getPreviousRate_expectTrue2() {
        var timestamp = 1624525200L;//2021-06-23
        var time = crs.getPrevDateFromTimestamp(timestamp);

        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 81.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 89.69));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(client.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(client.getYesterdayRate(eq(time), any())).thenReturn(yesterdayCurrencyResponse);
        crs.getCurrentRate("AFN");

        var actual = crs.getPreviousRate("AFN");
        var expected = 0.83;
        Assertions.assertEquals(expected, round(actual));
    }

    @Test
    void getPreviousRate_expectTrue3() {
        var timestamp = 1624525200L;//2021-06-23
        var time = crs.getPrevDateFromTimestamp(timestamp);

        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 81.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 1.69));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(client.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(client.getYesterdayRate(eq(time), any())).thenReturn(yesterdayCurrencyResponse);
        crs.getCurrentRate("AFN");

        var actual = crs.getPreviousRate("AFN");
        var expected = 44.03;
        Assertions.assertEquals(expected, round(actual));
    }

    @Test
    void getPreviousRate_expectArithmeticExc1() {
        var timestamp = 1624525200L;//2021-06-23
        var time = crs.getPrevDateFromTimestamp(timestamp);

        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 81.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 0.0));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(client.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(client.getYesterdayRate(eq(time), any())).thenReturn(yesterdayCurrencyResponse);
        crs.getCurrentRate("AFN");

        Assertions.assertThrows(ArithmeticException.class, () -> crs.getPreviousRate("AFN"));
    }

    @Test
    void getPreviousRate_expectIllegalArgumentExc1() {
        var timestamp = 1624525200L;//2021-06-23
        var time = crs.getPrevDateFromTimestamp(timestamp);

        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 81.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", -Double.MAX_VALUE));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(client.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(client.getYesterdayRate(eq(time), any())).thenReturn(yesterdayCurrencyResponse);
        crs.getCurrentRate("AFN");

        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getPreviousRate("AFN"));
    }

    @Test
    void getPreviousRate_expectIllegalArgumentExc2() {
        var timestamp = 1624525200L;//2021-06-23
        var time = crs.getPrevDateFromTimestamp(timestamp);

        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 81.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", -67.0001));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(client.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(client.getYesterdayRate(eq(time), any())).thenReturn(yesterdayCurrencyResponse);
        crs.getCurrentRate("AFN");

        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getPreviousRate("AFN"));
    }

    @Test
    void getPreviousRate_expectIllegalArgument() {
        var timestamp = 1624525200L;//2021-06-23
        var time = crs.getPrevDateFromTimestamp(timestamp);

        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 81.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", -67.0001));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(client.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(client.getYesterdayRate(eq(time), any())).thenReturn(yesterdayCurrencyResponse);
        crs.getCurrentRate("AFN");

        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.getPreviousRate("AAA"));
    }

}
