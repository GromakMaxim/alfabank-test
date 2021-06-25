package com.example.alfa.service;

import com.example.alfa.dto.api.response.GifResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class GifService {
    @Value("${api.gif.url-full1}")
    private String richURL;

    @Value("${api.gif.url-full2}")
    private String brokeURL;

    @Value("${api.gif.quality}")
    private String gifQuality;

    private RestTemplate restTemplate = new RestTemplate();

    public String getPicture(boolean isAbove) throws IOException {
        GifResponse gifResponse = null;
        if (isAbove) gifResponse = restTemplate.getForObject(richURL, GifResponse.class);
        if (!isAbove) gifResponse = restTemplate.getForObject(brokeURL, GifResponse.class);

        String url;
        if (gifQuality.equalsIgnoreCase("original")) url = gifResponse.getData().getImages().getOriginal().get("url");
        else url = gifResponse.getData().getImages().getDownsized().get("url");
        return url;
    }

}
