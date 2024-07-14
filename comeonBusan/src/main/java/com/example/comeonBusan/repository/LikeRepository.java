package com.example.comeonBusan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.comeonBusan.entity.Likes;


public interface LikeRepository extends JpaRepository<Likes, Long>{
	
	// 축제 좋아요
	Optional<Likes> findByFestivalUcSeq(Long uc_seq_long);
	
	
	 @Modifying
	 @Transactional
	 @Query("UPDATE Likes l SET l.likecount = :likecount WHERE l.festival.ucSeq = :ucSeq")
	 int updateLikeCountByFestivalUcSeq(@Param("likecount") Long likecount, @Param("ucSeq") Long ucSeq);

	
}
