package com.example.comeonBusanView.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.comeonBusanView.service.SearchBlogService;

@Controller
public class BlogSearchController {

	@Autowired
	private SearchBlogService blogService;
	
	@GetMapping("/blog")
	public String search(@RequestParam(name="업체명") String 업체명, Model model) {
		System.out.println("실행은 되나?");
		RestTemplate restTemplate = new RestTemplate();
        String restServerUrl = "http://localhost:8080/lodgments?업체명=" + 업체명;
        List<Map<String, Object>> lodgments = restTemplate.getForObject(restServerUrl, List.class);
        
        String query = lodgments.get(0).get("업체명").toString();
        String response = blogService.searchBlog(query);
        model.addAttribute("result", response);
        return "searchblog/blog";
	}
	
}
