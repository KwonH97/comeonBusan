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
	
	// 관광지 좋아요
	@Query("SELECT l FROM Likes l WHERE l.tourlist.uc_seq = :uc_seq")
	Optional<Likes> findByTourUcSeq(@Param("uc_seq") String uc_seq);
	
	//식당 좋아요 
	@Query("SELECT l FROM Likes l WHERE l.food.UC_SEQ = :UC_SEQ")
	Optional<Likes> findByFoodUcSeq(@Param("UC_SEQ")String uc_seq);
	
	
	 @Modifying
	 @Transactional
	 @Query("UPDATE Likes l SET l.likecount = :likecount WHERE l.festival.ucSeq = :ucSeq")
	 int updateLikeCountByFestivalUcSeq(@Param("likecount") Long likecount, @Param("ucSeq") Long ucSeq);
	 
	 // 축제 ㅈ호아요 카운트
	 
	 @Query("SELECT l.likecount FROM Likes l WHERE l.festival.ucSeq = :ucSeq")
	 Long findLikeCountByFestivalUcSeq(@Param("ucSeq") Long ucSeq);
	 
	@Query("SELECT l.likecount FROM Likes l WHERE l.tourlist.uc_seq = :uc_seq")
	Long findLikeCountByTourUcSeq(@Param("uc_seq") String uc_seq);
	
	@Query("SELECT l.likecount FROM Likes l WHERE l.food.UC_SEQ= :UC_SEQ")
	Long findLikeCountByFoodUcSeq(@Param("UC_SEQ") String uc_seq);
}
