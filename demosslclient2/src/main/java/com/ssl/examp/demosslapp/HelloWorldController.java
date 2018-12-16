package com.ssl.examp.demosslapp;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class HelloWorldController {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/consumerest")
	@ResponseBody
	public String consumeRest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return restTemplate
				.exchange("https://example.com:9191/hello-world?name=TestName", HttpMethod.GET, null, String.class)
				.getBody();
	}

	@GetMapping("/testing")
	@ResponseBody
	public String testingRest() {
		return "testing rest";
	}
}
