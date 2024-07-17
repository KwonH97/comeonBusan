package com.example.comeonBusan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.comeonBusan.entity.TourList;

public interface TourlistRepository extends JpaRepository<TourList, String>, JpaSpecificationExecutor<TourList>{
	
	
}
