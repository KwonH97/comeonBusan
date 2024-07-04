package com.example.comeonBusan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comeonBusan.entity.Food;

public interface FoodRepository extends JpaRepository<Food, String>{

}
