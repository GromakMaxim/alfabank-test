package com.example.alfa.api;

import com.example.alfa.feignclient.GifClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetGifTests {

    @Autowired
    private GifClient gifClient;

    @Value("${api.gif.id}")
    private String id;

    @Test
    @DisplayName("check if giphy api works properly")
    void getGif() {
        var response = gifClient.getGif(id, "rich", "g");
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response.getData()),
                () -> Assertions.assertNotNull(response.getData().getImages().getDownsized().get("url")),
                () -> Assertions.assertNotNull(response.getData().getImages().getOriginal().get("url")),
                () -> Assertions.assertNotNull(response)
        );
    }
}
