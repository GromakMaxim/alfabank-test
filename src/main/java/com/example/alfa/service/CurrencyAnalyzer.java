package com.example.alfa.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CurrencyAnalyzer {
    private GifService gifService;

    public CurrencyAnalyzer(GifService gifService) {
        this.gifService = gifService;
    }

    public String process(double currExchangeRate, double prevExchangeRate) throws IOException {
        var isAbove = compareCurrencies(currExchangeRate, prevExchangeRate);
        return gifService.getPicture(isAbove);
    }

    private boolean compareCurrencies(double currExchangeRate, double prevExchangeRate) {
        return currExchangeRate >= prevExchangeRate;
    }
}
