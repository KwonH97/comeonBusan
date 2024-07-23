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

    @GetMapping("/blog/lodgment")
    public Map<String, String> searchLodgment(@RequestParam(name="lid") Long lid, @RequestParam(name="page", defaultValue="1") int page, @RequestParam(name="size", defaultValue="10") int size, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String lodgmentUrl = "http://localhost:9002/lodgment/" + lid;
        Map<String, Object> lodgment = restTemplate.getForObject(lodgmentUrl, Map.class);

        if (lodgment != null && lodgment.containsKey("업체명")) {
            String 업체명 = lodgment.get("업체명").toString();
            String response = blogService.searchBlog(업체명, (page - 1) * size + 1, size);
            result.put("result", response);
            session.setAttribute("blogData", result);
        } else {
            result.put("result", "No lodgment data found");
            session.setAttribute("blogData", result);
        }

        return result;
    }

    @GetMapping("/blog/tour")
    public Map<String, String> searchTour(@RequestParam(name="uc_seq") Long uc_seq, @RequestParam(name="page", defaultValue="1") int page, @RequestParam(name="size", defaultValue="10") int size, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String tourUrl = "http://localhost:9002/kibTest/tour/" + uc_seq;
        Map<String, Object> tour = restTemplate.getForObject(tourUrl, Map.class);

        if (tour != null && tour.containsKey("title")) {
            String title = tour.get("title").toString();
            String response = blogService.searchBlog(title, (page - 1) * size + 1, size);
            result.put("result", response);
            session.setAttribute("blogData", result);
        } else {
            result.put("result", "No tour data found");
            session.setAttribute("blogData", result);
        }

        return result;
    }

    @GetMapping("/blog/festival")
    public Map<String, String> searchFestival(@RequestParam(name="uc_seq") Long uc_seq, @RequestParam(name="page", defaultValue="1") int page, @RequestParam(name="size", defaultValue="10") int size, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String festivalUrl = "http://localhost:9002/kibTest/festival/" + uc_seq;
        Map<String, Object> festival = restTemplate.getForObject(festivalUrl, Map.class);

        if (festival != null && festival.containsKey("title")) {
            String title = festival.get("title").toString();
            String response = blogService.searchBlog(title, (page - 1) * size + 1, size);
            result.put("result", response);
            session.setAttribute("blogData", result);
        } else {
            result.put("result", "No festival data found");
            session.setAttribute("blogData", result);
        }

        return result;
    }

    @GetMapping("/blog/restaurant")
    public Map<String, String> searchRestaurant(@RequestParam(name="uc_seq") Long uc_seq, @RequestParam(name="page", defaultValue="1") int page, @RequestParam(name="size", defaultValue="10") int size, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String restaurantUrl = "http://localhost:9002/kibTest/restaurant/" + uc_seq;
        Map<String, Object> restaurant = restTemplate.getForObject(restaurantUrl, Map.class);

        if (restaurant != null && restaurant.containsKey("title")) {
            String title = restaurant.get("title").toString();
            String response = blogService.searchBlog(title, (page - 1) * size + 1, size);
            result.put("result", response);
            session.setAttribute("blogData", result);
        } else {
            result.put("result", "No restaurant data found");
            session.setAttribute("blogData", result);
        }

        return result;
    }
}
