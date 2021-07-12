package com.example.alfa.service;

import com.example.alfa.dto.api.response.gif.GifResponse;
import com.example.alfa.feignclient.GifClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GifService {
    @Value("${api.gif.id}")
    private String id;

    @Value("${api.gif.rating}")
    private String rating;

    private GifClient gifClient;

    public GifService(GifClient gifClient) {
        this.gifClient = gifClient;
    }

    public String getPicture(boolean rateIsAbove) {
        GifResponse gifResponse;
        if (rateIsAbove) {
            gifResponse = gifClient.getGif(id, "rich", rating);
        } else {
            gifResponse = gifClient.getGif(id, "broke", rating);
        }
        return gifResponse.getData().getImages().getDownsized().get("url");
    }
}
