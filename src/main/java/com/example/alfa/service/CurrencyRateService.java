package com.example.alfa.service;

import com.example.alfa.dto.api.response.CurrencyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Service
public class CurrencyRateService {

    @Value("${api.currency.latest}")
    private String url;

    @Value("${api.currency.base}")
    private String base;

    @Value("${api.currency.historical}")
    private String historicalURL;

    @Value("${api.currency.id}")
    private String id;

    @Value("${api.currency.changing-base-parameter-is-enabled}")
    private boolean baseIsChangeable;

    private CurrencyAnalyzer analyzer;
    private CurrencyResponse currencyResponse;
    private RestTemplate restTemplate = new RestTemplate();

    public CurrencyRateService(CurrencyAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public String getRates(String code) throws IOException {
        var currExchangeRate = getCurrentRate(code);
        var prevExchangeRate = getPreviousRate(code);
        return analyzer.process(currExchangeRate, prevExchangeRate);
    }

    private double getCurrentRate(String code) {
        if (baseIsChangeable) {
            currencyResponse = sendRequestToAPI(url + "&base=" + base);
            var rate = currencyResponse.getRates().get(code);
            System.out.println("1 " + base + " ---> " + code + " " + rate);
            return rate;
        }
        currencyResponse = sendRequestToAPI(url);
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
            var url = historicalURL + date + ".json?app_id=" + id + "&base=" + base;
            var rate = sendRequestToAPI(url)
                    .getRates()
                    .get(code);
            System.out.println("1 " + base + " ---> " + code + " " + rate);
            return rate;
        }

        var url = historicalURL + date + ".json?app_id=" + id;
        var obj = sendRequestToAPI(url);

        var baseCurrencyRate = obj.getRates().get(base);
        var desiredCurrencyRate = obj.getRates().get(code);
        var rate = desiredCurrencyRate / baseCurrencyRate;
        System.out.println();
        System.out.println("previous");
        System.out.println(base + ": " + baseCurrencyRate + ", " + code + ": " + desiredCurrencyRate + " ==" + rate);

        return rate;
    }

    private CurrencyResponse sendRequestToAPI(String url){
        return restTemplate.getForObject(url, CurrencyResponse.class);
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
