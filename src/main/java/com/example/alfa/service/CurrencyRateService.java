package com.example.alfa.service;

import com.example.alfa.dto.api.response.currency.CurrencyResponse;
import com.example.alfa.feignclient.CurrencyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CurrencyRateService {

    @Value("${api.currency.id}")
    private String id;

    @Value("${api.currency.desired}")
    private String desiredCurrency;

    private CurrencyAnalyzer analyzer;
    private CurrencyResponse currencyResponse;
    private CurrencyClient currencyClient;

    public CurrencyRateService(CurrencyAnalyzer analyzer, CurrencyClient currencyClient) {
        this.analyzer = analyzer;
        this.currencyClient = currencyClient;
    }

    public void setDesiredCurrency(String cur) {
        if (desiredCurrency == null) this.desiredCurrency = cur;
    }

    public String checkRatesAndGetGifLink(String code) throws IOException {
        code = code.toUpperCase();
        var currExchangeRate = getCurrentRate(code);
        var prevExchangeRate = getPreviousRate(code);
        System.out.println("todayRate: " + currExchangeRate);
        System.out.println("yesterdayRate: " + prevExchangeRate);
        return analyzer.process(currExchangeRate, prevExchangeRate);
    }

    double getCurrentRate(String code) {
        try {
            currencyResponse = currencyClient.getCurrentRate(id);

            var desiredCurrencyRate = currencyResponse.getRates().get(this.desiredCurrency);
            var usersCurrencyRate = currencyResponse.getRates().get(code);
            System.out.println("today " + desiredCurrency + ": " + desiredCurrencyRate);
            System.out.println("today " + code + ": " + usersCurrencyRate);
            return calculateCrossCurrencyRate(desiredCurrencyRate, usersCurrencyRate);
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("cant find such currency code in response");
        }
    }

    double calculateCrossCurrencyRate(double desiredCurrency, double usersCurrency) {
        if (desiredCurrency < 0 || usersCurrency < 0) throw new IllegalArgumentException("currency < 0");
        if (desiredCurrency == 0) throw new ArithmeticException(this.desiredCurrency + " is 0");
        if (usersCurrency == 0) throw new ArithmeticException("rate is 0");

        desiredCurrency = 1 / desiredCurrency;
        usersCurrency = 1 / usersCurrency;
        return usersCurrency / desiredCurrency;
    }

    double getPreviousRate(String code) {
        try {
            var date = getPrevDateFromTimestamp(currencyResponse.getTimestamp());
            var cr = currencyClient.getYesterdayRate(date, id);

            var desiredCurrencyRate = cr.getRates().get(this.desiredCurrency);
            var usersCurrencyRate = cr.getRates().get(code);

            System.out.println("yesterday " + desiredCurrency + ": " + desiredCurrencyRate);
            System.out.println("yesterday " + code + ": " + usersCurrencyRate);

            return calculateCrossCurrencyRate(desiredCurrencyRate, usersCurrencyRate);
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("cant find such currency code in response");
        }
    }

    String getPrevDateFromTimestamp(long timestamp) {
        var today = new Date(timestamp * 1000);
        var yesterday = new Date(today.getTime() - 24 * 3600 * 1000);
        var sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(yesterday.getTime());
    }
}