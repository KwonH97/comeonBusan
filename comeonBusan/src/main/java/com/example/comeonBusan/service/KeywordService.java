package com.example.comeonBusan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comeonBusan.entity.SearchKeyword;
import com.example.comeonBusan.repository.SearchKeywordRepository;

@Service
public class KeywordService {

    @Autowired
    private SearchKeywordRepository searchKeywordRepository;
    
    public List<SearchKeyword> getSuggestions(String query) {
        return searchKeywordRepository.findByKeywordStartingWithOrderBySearchCountDesc(query);
    }
    
    public void saveOrUpdateSearchKeyword(String keyword) {
        System.out.println("Keyword저장---------------------------");
        SearchKeyword searchKeyword = searchKeywordRepository.findByKeyword(keyword)
                .orElse(new SearchKeyword());
        if (searchKeyword.getSid() == null) {
            searchKeyword.setKeyword(keyword);
            searchKeyword.setSearchCount(1L);
        } else {
            searchKeyword.setSearchCount(searchKeyword.getSearchCount() + 1);
        }
        searchKeywordRepository.save(searchKeyword);
    }
}
