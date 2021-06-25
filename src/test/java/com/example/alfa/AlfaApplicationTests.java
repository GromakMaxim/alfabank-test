package com.example.alfa;

import com.example.alfa.controller.CurrencyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AlfaApplicationTests {

    @Autowired
    private CurrencyController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
