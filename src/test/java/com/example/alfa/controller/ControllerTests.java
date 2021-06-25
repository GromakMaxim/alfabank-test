package com.example.alfa.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void showGif_expect303Redirect() {
        var response = restTemplate.getForEntity("http://localhost:" + port + "/?code=ANG", ResponseEntity.class);
        var isRedirected = response.getStatusCode().is3xxRedirection();
        Assertions.assertTrue(isRedirected);
    }
}
