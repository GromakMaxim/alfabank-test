package com.example.alfa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertiesTest {
    @Value("${api.currency.id}")
    private String currencyId;

    @Value("${api.gif.id}")
    private String gifId;

    @Value("${api.currency.url.openexchangerates}")
    private String currencyLink;

    @Value("${api.gif.url.giphy-random}")
    private String gifLink;

    @Value("${api.currency.base}")
    private String base;

    @Value("${api.gif.rating}")
    private String rating;

    @Value("${api.currency.desired}")
    private String desiredCurrency;

    @Test
    @DisplayName("checking whether all options are assigned")
    void checkOptions() {
        Assertions.assertNotNull(currencyId);
        Assertions.assertNotNull(gifId);

        Assertions.assertNotNull(currencyLink);
        Assertions.assertNotNull(gifLink);

        Assertions.assertNotNull(base);
        Assertions.assertNotNull(rating);
        Assertions.assertNotNull(desiredCurrency);

    }


}
