package com.example.alfa.feignclient;

import com.example.alfa.dto.api.response.CurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency-client", url = "${api.currency.main}")
@Component
public interface CurrencyClient {
    @GetMapping("/historical/{date}.json")
    CurrencyResponse getYesterdayRate(@PathVariable("date") String date,
                                      @RequestParam("app_id") String id,
                                      @RequestParam("base") String base);

    @GetMapping("/historical/{date}.json")
    CurrencyResponse getYesterdayRate(@PathVariable("date") String date,
                                      @RequestParam("app_id") String id);

    @GetMapping("/latest.json")
    CurrencyResponse getCurrentRate(@RequestParam("app_id") String id,
                                    @RequestParam("base") String base);

    @GetMapping("/latest.json")
    CurrencyResponse getCurrentRate(@RequestParam("app_id") String id);
}

