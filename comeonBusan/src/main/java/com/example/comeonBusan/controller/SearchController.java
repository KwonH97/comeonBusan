package com.example.comeonBusan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.dto.SearchResult;
import com.example.comeonBusan.entity.SearchKeyword;
import com.example.comeonBusan.service.KeywordService;
import com.example.comeonBusan.service.SearchService;

@RestController
public class SearchController {

	@Autowired
	private KeywordService keywordService;
	
	@Autowired
    private SearchService searchService;
	
	@GetMapping("/autocomplete/{query}")
	public List<SearchKeyword> autocomplete(@PathVariable(name ="query") String query){
		
		return keywordService.getSuggestions(query);
	}
	
	@PostMapping("/search/{query}")
	public List<SearchKeyword> search(@PathVariable(name ="query") String query) {
		
		keywordService.saveOrUpdateSearchKeyword(query);
		
        return keywordService.getSuggestions(query);
    }
	
	@GetMapping("/search")
    public List<SearchResult> search2(@RequestParam(name = "query") String query) {
        return searchService.search(query);
    }
}
