package com.example.comeonBusan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comeonBusan.entity.Likes;

public interface LikeRepository extends JpaRepository<Likes, Long>{

}
