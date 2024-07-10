package com.example.comeonBusan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.comeonBusan.entity.Lodgment;
import com.example.comeonBusan.repository.LodgmentRepository;

@Service
public class LodgmentService {

	  @Autowired
	  private LodgmentRepository lodgmentRepository;
	  
	  public List<Lodgment> getLodgmentList(){
		  
		  return lodgmentRepository.findAll();
	  }
	  
	  public Page<Lodgment> LodgmentPage(Pageable pageable){
		  return lodgmentRepository.findAll(pageable);
	  }
	  
	  public Lodgment saveLodgment(Lodgment lodgment) {
		  return lodgmentRepository.save(lodgment);
	  }
}
