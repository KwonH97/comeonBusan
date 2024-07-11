package com.example.comeonBusan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comeonBusan.dto.FestivalDto;
import com.example.comeonBusan.repository.FestivalRepository;

@Service
public class FestivalService {

	@Autowired
	private FestivalRepository festivalRepository;
	
	public List<FestivalDto> getFestivalsStartingThisMonth(){
		
		return festivalRepository.findFestivalsStartingThisMonthAsDTO();
	}
	
}
