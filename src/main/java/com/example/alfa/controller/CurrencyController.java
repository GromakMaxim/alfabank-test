package com.example.alfa.controller;

import com.example.alfa.service.CurrencyRateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class CurrencyController {
	private CurrencyRateService service;

	public CurrencyController(CurrencyRateService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity greetingForm(@RequestParam("code") String code) throws URISyntaxException, IOException {
		URI uri = new URI(service.getRates(code));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uri);
		return new ResponseEntity(httpHeaders, HttpStatus.SEE_OTHER);
	}

}
