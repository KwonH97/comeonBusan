package com.example.comeonBusan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.comeonBusan.entity.Lodgment;

public interface LodgmentRepository extends JpaRepository<Lodgment, Long>, JpaSpecificationExecutor<Lodgment>{

	List<Lodgment> findBy업체명(String 업체명);
}
