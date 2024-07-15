package com.example.comeonBusan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comeonBusan.entity.Lodgment;

public interface LodgmentRepository extends JpaRepository<Lodgment, Long>{

	List<Lodgment> findBy업체명(String 업체명);
}
