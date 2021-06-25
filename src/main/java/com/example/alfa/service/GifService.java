package com.example.alfa.service;

import com.example.alfa.dto.api.response.GifResponse;
import com.example.alfa.feignclient.GifClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GifService {
    @Value("${api.gif.id}")
    private String id;

    @Value("${api.gif.rating}")
    private String rating;

    @Value("${api.gif.quality}")
    private String gifQuality;

    private GifClient gifClient;

    public GifService(GifClient gifClient) {
        this.gifClient = gifClient;
    }

    public String getPicture(boolean rateIsAbove) {
        GifResponse gifResponse = null;
        if (rateIsAbove) gifResponse = gifClient.getGif(id, "rich", rating);
        if (!rateIsAbove) gifResponse = gifClient.getGif(id, "broke", rating);

        String url;
        if (gifQuality.equalsIgnoreCase("original")) url = gifResponse.getData().getImages().getOriginal().get("url");
        else url = gifResponse.getData().getImages().getDownsized().get("url");
        return url;
    }

}
