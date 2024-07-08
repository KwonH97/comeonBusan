package com.example.comeonBusan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comeonBusan.entity.SearchKeyword;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long>{

	List<SearchKeyword> findBykeywordStartingWith(String SearchKeyword);
	Optional<SearchKeyword> findByKeyword(String keyword);
	
}
