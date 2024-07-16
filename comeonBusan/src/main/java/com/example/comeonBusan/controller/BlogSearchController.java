package com.example.comeonBusan.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.comeonBusan.service.SearchBlogService;

import jakarta.servlet.http.HttpSession;

@RestController
public class BlogSearchController {

    @Autowired
    private SearchBlogService blogService;

    @GetMapping("/blog")
    public Map<String, String> searchlodgment(@RequestParam(name="lid") Long lid, HttpSession session) {
        Map<String, String> result = new HashMap<>();

        RestTemplate restTemplate = new RestTemplate();
        String lodgmentUrl = "http://localhost:9002/lodgment/" + lid;
        Map<String, Object> lodgment = restTemplate.getForObject(lodgmentUrl, Map.class);

        if (lodgment != null && lodgment.containsKey("업체명")) {
            String 업체명 = lodgment.get("업체명").toString();
            String response = blogService.searchBlog(업체명);
            result.put("result", response);
        } else {
            result.put("result", "No lodgment data found");
        }

        session.setAttribute("blogData", result);
        return result;
    }
}
