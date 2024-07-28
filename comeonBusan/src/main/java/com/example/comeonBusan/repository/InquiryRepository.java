package com.example.comeonBusan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.comeonBusan.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>{

	@Query("SELECT i FROM Inquiry i WHERE i.state = 0")
    List<Inquiry> findInquiryStateIsZero();
	
	@Modifying
	@Query("UPDATE Inquiry i SET i.state = 1 WHERE i.ino =:ino")
	void updateStateByIno(@Param("ino") Long ino);
	
}
