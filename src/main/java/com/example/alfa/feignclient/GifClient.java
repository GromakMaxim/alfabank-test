package com.example.alfa.feignclient;

import com.example.alfa.dto.api.response.GifResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gif-client", url = "${api.gif.random}")
@Component
public interface GifClient {
    @GetMapping(value = "/random")
    GifResponse getGif(@RequestParam("api_key") String id,
                       @RequestParam("tag") String tag,
                       @RequestParam("rating") String rating);

    @GetMapping(value = "/random")
    GifResponse downloadGif(@RequestParam("api_key") String id,
                       @RequestParam("tag") String tag,
                       @RequestParam("rating") String rating);
}
