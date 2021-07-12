package com.example.alfa.service;

import com.example.alfa.dto.api.response.currency.CurrencyResponse;
import com.example.alfa.dto.api.response.gif.GifResponse;
import com.example.alfa.dto.obj.Gif;
import com.example.alfa.dto.obj.Image;
import com.example.alfa.feignclient.CurrencyClient;
import com.example.alfa.feignclient.GifClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CurrencyRateServiceTest {
    private GifClient gifClient = mock(GifClient.class);
    private CurrencyClient currencyClient = mock(CurrencyClient.class);
    private CurrencyAnalyzer analyzer;
    private CurrencyRateService crs;
    private Long timestamp = 1624525200L;//2021-06-23

    @BeforeEach
    void init() {
        analyzer = new CurrencyAnalyzer(new GifService(gifClient));
        crs = new CurrencyRateService(analyzer, currencyClient);
        crs.setDesiredCurrency("RUB");
    }

    private HashMap<String, Double> createTestMap(String name, Double cur) {
        var map = new HashMap<String, Double>();
        map.put(name, cur);
        map.put("RUB", 74.41);
        return map;
    }

    private Image createDownsizedImg(String tag) {
        var downsizedMap = new HashMap<String, String>();
        var img = new Image();

        if (tag.equals("rich")) {
            downsizedMap.put("height", "450");
            downsizedMap.put("size", "1536445");
            downsizedMap.put("url", "https://media0.giphy.com/media/l0HlNbr0r16WgZmbC/giphy.gif?cid=9fdcd795431f0cffa9e32daeabfec42005a9967b09d3c2ca&rid=giphy.gif&ct=g");
            downsizedMap.put("width", "600");
            img.setDownsized(downsizedMap);
        } else if (tag.equals("broke")) {
            downsizedMap.put("height", "360");
            downsizedMap.put("size", "2036554");
            downsizedMap.put("url", "https://media1.giphy.com/media/nV2YBxwSNc1iM/giphy.gif?cid=9fdcd7954d97f998a44efaaa248c3abacc0b128f8ade2c30&rid=giphy.gif&ct=g");
            downsizedMap.put("width", "480");
            img.setDownsized(downsizedMap);
        }
        return img;
    }

    private GifResponse createGifResponse(String tag) {
        var gifResponse = new GifResponse();
        var gif = new Gif();
        gif.setId("l0HlNbr0r16WgZmbC");
        gif.setTitle("Pay Me Make It Rain GIF by Originals");
        gif.setType("gif");
        gif.setImages(createDownsizedImg(tag));
        gifResponse.setData(gif);
        return gifResponse;
    }

    @Test
    void getRates_Rich_ExpectTrue() throws IOException {
        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 81.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 89.69));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(currencyClient.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(currencyClient.getYesterdayRate(anyString(), any())).thenReturn(yesterdayCurrencyResponse);
        when(gifClient.getGif(any(), eq("rich"), any())).thenReturn(createGifResponse("rich"));

        var actual = crs.checkRatesAndGetGifLink("AFN");
        var expected = "https://media0.giphy.com/media/l0HlNbr0r16WgZmbC/giphy.gif?cid=9fdcd795431f0cffa9e32daeabfec42005a9967b09d3c2ca&rid=giphy.gif&ct=g";

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRates_Broke_ExpectTrue() throws IOException {
        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 90.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 89.69));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(currencyClient.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(currencyClient.getYesterdayRate(anyString(), any())).thenReturn(yesterdayCurrencyResponse);
        when(gifClient.getGif(any(), eq("broke"), any())).thenReturn(createGifResponse("broke"));

        var actual = crs.checkRatesAndGetGifLink("AFN");
        var expected = "https://media1.giphy.com/media/nV2YBxwSNc1iM/giphy.gif?cid=9fdcd7954d97f998a44efaaa248c3abacc0b128f8ade2c30&rid=giphy.gif&ct=g";

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRates_EqualCurrencies_ExpectTrue1() throws IOException {
        var todayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 89.69));
        var yesterdayCurrencyResponse = new CurrencyResponse(createTestMap("AFN", 89.69));
        todayCurrencyResponse.setTimestamp(timestamp);

        when(currencyClient.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(currencyClient.getYesterdayRate(anyString(), any())).thenReturn(yesterdayCurrencyResponse);
        when(gifClient.getGif(any(), eq("rich"), any())).thenReturn(createGifResponse("rich"));

        var actual = crs.checkRatesAndGetGifLink("AFN");
        var expected = "https://media0.giphy.com/media/l0HlNbr0r16WgZmbC/giphy.gif?cid=9fdcd795431f0cffa9e32daeabfec42005a9967b09d3c2ca&rid=giphy.gif&ct=g";

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRates_EqualCurrencies_ExpectTrue2() throws IOException {
        var todayMap = new HashMap<String, Double>();
        todayMap.put("AFN", 81.69);
        todayMap.put("RUB", 74.41);

        var todayCurrencyResponse = new CurrencyResponse(todayMap);

        var yesterdayMap = new HashMap<String, Double>();
        yesterdayMap.put("AFN", 81.69);
        yesterdayMap.put("RUB", 100.5);

        var yesterdayCurrencyResponse = new CurrencyResponse(yesterdayMap);
        todayCurrencyResponse.setTimestamp(timestamp);

        when(currencyClient.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(currencyClient.getYesterdayRate(anyString(), any())).thenReturn(yesterdayCurrencyResponse);
        when(gifClient.getGif(any(), eq("broke"), any())).thenReturn(createGifResponse("broke"));

        var actual = crs.checkRatesAndGetGifLink("AFN");
        var expected = "https://media1.giphy.com/media/nV2YBxwSNc1iM/giphy.gif?cid=9fdcd7954d97f998a44efaaa248c3abacc0b128f8ade2c30&rid=giphy.gif&ct=g";

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRates_Equal_ExpectTrue() throws IOException {
        var todayMap = new HashMap<String, Double>();
        todayMap.put("AFN", 81.69);
        todayMap.put("RUB", 81.69);

        var todayCurrencyResponse = new CurrencyResponse(todayMap);

        var yesterdayMap = new HashMap<String, Double>();
        yesterdayMap.put("AFN", 81.69);
        yesterdayMap.put("RUB", 81.69);

        var yesterdayCurrencyResponse = new CurrencyResponse(yesterdayMap);
        todayCurrencyResponse.setTimestamp(timestamp);

        when(currencyClient.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(currencyClient.getYesterdayRate(anyString(), any())).thenReturn(yesterdayCurrencyResponse);
        when(gifClient.getGif(any(), eq("rich"), any())).thenReturn(createGifResponse("rich"));

        var actual = crs.checkRatesAndGetGifLink("AFN");
        var expected = "https://media0.giphy.com/media/l0HlNbr0r16WgZmbC/giphy.gif?cid=9fdcd795431f0cffa9e32daeabfec42005a9967b09d3c2ca&rid=giphy.gif&ct=g";

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getRates_Equal_ExpectArithmeticExc() throws IOException {
        var todayMap = new HashMap<String, Double>();
        todayMap.put("AFN", 0.0);
        todayMap.put("RUB", 0.0);

        var todayCurrencyResponse = new CurrencyResponse(todayMap);

        var yesterdayMap = new HashMap<String, Double>();
        yesterdayMap.put("AFN", 0.0);
        yesterdayMap.put("RUB", 0.0);

        var yesterdayCurrencyResponse = new CurrencyResponse(yesterdayMap);
        todayCurrencyResponse.setTimestamp(timestamp);

        when(currencyClient.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(currencyClient.getYesterdayRate(anyString(), any())).thenReturn(yesterdayCurrencyResponse);
        when(gifClient.getGif(any(), eq("rich"), any())).thenReturn(createGifResponse("rich"));

        Assertions.assertThrows(ArithmeticException.class, () -> crs.checkRatesAndGetGifLink("AFN"));
    }

    @Test
    void getRates_Equal_ExpectIllegalArgumentExc() throws IOException {
        var todayMap = new HashMap<String, Double>();
        todayMap.put("AFN", -10.0);
        todayMap.put("RUB", -20.0);

        var todayCurrencyResponse = new CurrencyResponse(todayMap);

        var yesterdayMap = new HashMap<String, Double>();
        yesterdayMap.put("AFN", -900.0);
        yesterdayMap.put("RUB", 45.0);

        var yesterdayCurrencyResponse = new CurrencyResponse(yesterdayMap);
        todayCurrencyResponse.setTimestamp(timestamp);

        when(currencyClient.getCurrentRate(any())).thenReturn(todayCurrencyResponse);
        when(currencyClient.getYesterdayRate(anyString(), any())).thenReturn(yesterdayCurrencyResponse);
        when(gifClient.getGif(any(), eq("rich"), any())).thenReturn(createGifResponse("rich"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> crs.checkRatesAndGetGifLink("AFN"));
    }

}