package com.example.comeonBusanView.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainViewController {
	
	@Value("${weather.api.key}")
	private String weatherApiKey;
	
	@Value("${ipinfo.api.token}")
	private String ipToken;
	
	// ComeonBusanViewApplication.java 에서 RestTemplate 빈으로 등록시켜야 에러안남
	private final RestTemplate restTemplate;

	public MainViewController(RestTemplate restTemplate) {

		this.restTemplate = restTemplate;
	}
	
	@GetMapping("/main")
	public String main() {
		return "/mainPage/main";
	}
	
	@GetMapping("/proxy/main-weather-api")
	public ResponseEntity<String> proxyWeatherApi(@RequestParam Map<String, String> params) {

		StringBuilder url = new StringBuilder("http://api.openweathermap.org/data/2.5/forecast?appid=" + weatherApiKey);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
		}
		return restTemplate.getForEntity(url.toString(), String.class);

	}
	
	@GetMapping("/proxy/ipToken")
    public ResponseEntity<String> proxyIpToken() {
        StringBuilder url = new StringBuilder("https://ipinfo.io?token=" + ipToken);
        return restTemplate.getForEntity(url.toString(), String.class);
    }
}
