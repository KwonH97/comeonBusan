package com.example.comeonBusan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.entity.SearchKeyword;
import com.example.comeonBusan.service.KeywordService;

@CrossOrigin("*")
@RestController
public class KHController {

	@Autowired
	private KeywordService keywordService;
	
	@GetMapping("/autocomplete/{query}")
	public List<SearchKeyword> autocomplete(@PathVariable(name ="query") String query){
		System.out.println("autocomplete---------------");
		return keywordService.getSuggestions(query);
	}
	
	@PostMapping("/search/{query}")
	public List<SearchKeyword> search(@PathVariable(name ="query") String query) {
		System.out.println("search---------------");
		keywordService.saveOrUpdateSearchKeyword(query);
		
        return keywordService.getSuggestions(query);
    }
	
}
