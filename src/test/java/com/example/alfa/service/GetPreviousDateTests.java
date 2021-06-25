package com.example.alfa.service;

import com.example.alfa.service.CurrencyRateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest
class GetPreviousDateTests {

    @Autowired
    private CurrencyRateService currencyRateService;

    @Test
    @DisplayName("try with valid timestamp 2021-06-23")
    void test_getPrevDateFromTimestamp1_expectTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        var cl = CurrencyRateService.class;
        var m = cl.getDeclaredMethod("getPrevDateFromTimestamp", long.class);
        m.setAccessible(true);
        var actual = m.invoke(currencyRateService, 1624525200);
        var expected = "2021-06-23";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("try with valid timestamp 2015-12-11")
    void test_getPrevDateFromTimestamp2_expectTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var cl = CurrencyRateService.class;
        var m = cl.getDeclaredMethod("getPrevDateFromTimestamp", long.class);
        m.setAccessible(true);
        var actual = m.invoke(currencyRateService, 1449877801);
        var expected = "2015-12-11";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("try with valid timestamp 1998-12-31")
    void test_getPrevDateFromTimestamp3_expectTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var cl = CurrencyRateService.class;
        var m = cl.getDeclaredMethod("getPrevDateFromTimestamp", long.class);
        m.setAccessible(true);
        var actual = m.invoke(currencyRateService, 915177407);
        var expected = "1998-12-31";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("try with timestamp 0")
    void test_getPrevDateFromTimestamp_Zero_expectTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var cl = CurrencyRateService.class;
        var m = cl.getDeclaredMethod("getPrevDateFromTimestamp", long.class);
        m.setAccessible(true);
        var actual = m.invoke(currencyRateService, 0);
        var expected = "1969-12-31";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("try with timestamp -100")
    void test_getPrevDateFromTimestamp5_BelowZeroExpectTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var cl = CurrencyRateService.class;
        var method = cl.getDeclaredMethod("getPrevDateFromTimestamp", long.class);
        method.setAccessible(true);
        var actual = method.invoke(currencyRateService, -100);
        var expected = "1969-12-31";
        System.out.println(actual);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("try with timestamp with very big number")
    void test_getPrevDateFromTimestamp5_BigNumberExpectTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var cl = CurrencyRateService.class;
        var method = cl.getDeclaredMethod("getPrevDateFromTimestamp", long.class);
        method.setAccessible(true);
        var actual = method.invoke(currencyRateService, 9_223_372_036_854_775_807L);
        var expected = "1969-12-31";
        System.out.println(actual);
        Assertions.assertEquals(actual, expected);
    }
}