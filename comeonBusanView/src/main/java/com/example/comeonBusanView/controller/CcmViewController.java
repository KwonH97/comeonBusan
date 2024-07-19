package com.example.comeonBusanView.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class CcmViewController {
    
	@Value("${google.maps.api.key}")
	private String googleMapsApiKey;

	@Value("${weather.api.key}")
	private String weatherApiKey;
	
	// ComeonBusanViewApplication.java 에서 RestTemplate 빈으로 등록시켜야 에러안남
	private final RestTemplate restTemplate;

	public CcmViewController(RestTemplate restTemplate) {

		this.restTemplate = restTemplate;
	}
	
	// 날씨
	@GetMapping("/proxy/weather-api")
	public ResponseEntity<String> proxyWeatherApi(@RequestParam Map<String, String> params) {

		StringBuilder url = new StringBuilder("http://api.openweathermap.org/data/2.5/forecast?appid=" + weatherApiKey);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
		}
		return restTemplate.getForEntity(url.toString(), String.class);

	}
	
	// 지도
    @GetMapping("/proxy/maps-api")
	public ResponseEntity<String> proxyMapsApi(@RequestParam Map<String, String> params) {
		StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/js?key=" + googleMapsApiKey);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
		}
		return restTemplate.getForEntity(url.toString(), String.class);
	}
    
	
    @GetMapping("/weather")
    public String loadWeather() {
        return "weather";
    }
       
    @GetMapping("/showMap")
    public String showMap() {
        return "mapTest";
    }
    
    @GetMapping("/showTheWay")
    public String showTheWay() {
        return "showTheWay";
    }
    
    @GetMapping("/roadView")
    public String roadView() {
        return "roadView";
    }
    
    @GetMapping("/rainInfo")
    public String rainInfo() {
        return "rainInfo";
    }
    
    @GetMapping("/recommand")
    public String recommand() {
        return "recommand";
    }
    
    @GetMapping("/translate")
    public String translate() {
        return "translate";
    }
    
   
    
    @GetMapping("/safeArea")
    public String safeArea() {
        return "safeArea";
    }
    
    @GetMapping("/cityTour")
    public String cityTour() {
        return "cityTour";
    }


}