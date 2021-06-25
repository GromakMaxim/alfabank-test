package com.example.alfa.dto.api.response;

import java.util.Map;

public class CurrencyResponse {
    private Long timestamp;
    private String base;
    private Map<String, Double> rates;

    public Map<String, Double> getRates() {
        return rates;
    }

    public CurrencyResponse(Map<String, Double> rates) {
        this.rates = rates;
    }

    public CurrencyResponse() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
