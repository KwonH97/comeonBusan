package com.example.comeonBusan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.comeonBusan.entity.Views;

public interface ViewsRepository extends JpaRepository<Views, Long> {

    @Query("SELECT v FROM Views v WHERE v.tourlist.uc_seq = :tourlistUcSeq")
    Views findByTourlistUcSeq(@Param("tourlistUcSeq") String tourlistUcSeq);

    @Query("SELECT v FROM Views v WHERE v.festival.ucSeq = :festivalUcSeq")
    Views findByFestivalUcSeq(@Param("festivalUcSeq") Long festivalUcSeq);

    @Query("SELECT v FROM Views v WHERE v.food.UC_SEQ = :foodUcSeq")
    Views findByFoodUcSeq(@Param("foodUcSeq") String foodUcSeq);
}
