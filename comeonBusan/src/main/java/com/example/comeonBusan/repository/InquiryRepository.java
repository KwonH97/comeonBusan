package com.example.comeonBusan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.comeonBusan.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>{

	@Query("SELECT i FROM Inquiry i WHERE i.state = 0")
    List<Inquiry> findInquiryStateIsZero();
	
}
