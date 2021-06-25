package com.example.alfa.service;

import com.example.alfa.dto.api.response.CurrencyResponse;
import com.example.alfa.feignclient.CurrencyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Service
public class CurrencyRateService {

    @Value("${api.currency.base}")
    private String base;

    @Value("${api.currency.id}")
    private String id;

    @Value("${api.currency.changing-base-parameter-is-enabled}")
    private boolean baseIsChangeable;

    private CurrencyAnalyzer analyzer;
    private CurrencyResponse currencyResponse;
    private CurrencyClient currencyClient;

    public CurrencyRateService(CurrencyAnalyzer analyzer, CurrencyClient currencyClient) {
        this.analyzer = analyzer;
        this.currencyClient = currencyClient;
    }

    public String getRates(String code) throws IOException {
        var currExchangeRate = getCurrentRate(code);
        var prevExchangeRate = getPreviousRate(code);
        return analyzer.process(currExchangeRate, prevExchangeRate);
    }

    private double getCurrentRate(String code) {
        if (baseIsChangeable) {
            currencyResponse = currencyClient.getCurrentRate(id, base);
            var rate = currencyResponse.getRates().get(code);
            System.out.println("1 " + base + " ---> " + code + " " + rate);
            return rate;
        }
        currencyResponse = currencyClient.getCurrentRate(id);
        var baseCurrencyRate = currencyResponse.getRates().get(base);
        var desiredCurrencyRate = currencyResponse.getRates().get(code);
        var rate = desiredCurrencyRate / baseCurrencyRate;
        System.out.println("current");
        System.out.println(base + ": " + baseCurrencyRate + ", " + code + ": " + desiredCurrencyRate + " ==" + rate);
        return rate;
    }

    private double getPreviousRate(String code) {
        var date = getPrevDateFromTimestamp(currencyResponse.getTimestamp());
        if (baseIsChangeable) {
            var rate = currencyClient.getYesterdayRate(date, id, base)
                    .getRates()
                    .get(code);
            System.out.println("1 " + base + " ---> " + code + " " + rate);
            return rate;
        }

        var obj = currencyClient.getYesterdayRate(date, id);

        var baseCurrencyRate = obj.getRates().get(base);
        var desiredCurrencyRate = obj.getRates().get(code);
        var rate = desiredCurrencyRate / baseCurrencyRate;
        System.out.println();
        System.out.println("previous");
        System.out.println(base + ": " + baseCurrencyRate + ", " + code + ": " + desiredCurrencyRate + " ==" + rate);
        return rate;
    }

    private String getPrevDateFromTimestamp(long timestamp) {
        Date currentDate = new Date(timestamp * 1_000);
        Calendar c = new GregorianCalendar();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_YEAR, -1);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(c.getTime());
    }
}
