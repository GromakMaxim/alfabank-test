package com.example.alfa.api;

import com.example.alfa.feignclient.CurrencyClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetCurrencyRateTests {

    @Autowired
    private CurrencyClient currencyClient;

    @Value("${api.currency.id}")
    private String id;

    @Test
    @DisplayName("check if openexchangerates.org works properly")
    void getCurrentRate() {
        var response = currencyClient.getCurrentRate(id);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response.getRates()),
                () -> Assertions.assertNotNull(response.getBase()),
                () -> Assertions.assertNotNull(response.getTimestamp()),
                () -> Assertions.assertNotNull(response.getRates().get("USD")),
                () -> Assertions.assertNotNull(response.getRates().get("RUB"))
        );
    }

    @Test
    @DisplayName("check if openexchangerates.org works properly")
    void getYesterdayRate() {
        var response = currencyClient.getYesterdayRate("2021-06-23", id);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response.getRates()),
                () -> Assertions.assertNotNull(response.getBase()),
                () -> Assertions.assertNotNull(response.getTimestamp()),
                () -> Assertions.assertNotNull(response.getRates().get("USD")),
                () -> Assertions.assertNotNull(response.getRates().get("RUB"))
        );
    }

}
