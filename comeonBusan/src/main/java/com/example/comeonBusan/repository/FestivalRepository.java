package com.example.comeonBusan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comeonBusan.entity.Festival;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
}