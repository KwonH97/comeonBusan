package com.example.comeonBusan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.comeonBusan.entity.Food;

public interface FoodRepository extends JpaRepository<Food, String>, JpaSpecificationExecutor<Food>{

}
