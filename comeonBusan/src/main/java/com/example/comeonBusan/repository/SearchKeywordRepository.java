package com.example.comeonBusan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.comeonBusan.entity.SearchKeyword;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {

    List<SearchKeyword> findByKeywordStartingWith(String searchKeyword);
    
    Optional<SearchKeyword> findByKeyword(String keyword);
    
    @Query("SELECT sk FROM SearchKeyword sk WHERE sk.keyword LIKE :keyword% ORDER BY sk.searchCount DESC")
    List<SearchKeyword> findByKeywordStartingWithOrderBySearchCountDesc(@Param("keyword") String keyword);
}
