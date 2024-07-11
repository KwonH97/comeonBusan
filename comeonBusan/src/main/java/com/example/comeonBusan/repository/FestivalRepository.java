package com.example.comeonBusan.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.comeonBusan.dto.FestivalDto;
import com.example.comeonBusan.entity.Festival;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
	
	@Query(value ="select f.place, f.main_img_thumb, f.startDate, f.endDate from festival f where MONTH(f.startDate) = MONTH(curdate())", nativeQuery = true)
	public List<Object[]> findFestivalsStartingThisMonth();

	
	default List<FestivalDto> findFestivalsStartingThisMonthAsDTO(){
		List<Object[]> results = findFestivalsStartingThisMonth();
		
		List<FestivalDto> dtos = new ArrayList<>();
		
		for(Object[] result : results) {
			
			FestivalDto dto = new FestivalDto(
					
					(String) result[0], //place
					(String) result[1], // main_img_thumb
					(Date) result[2], // startDate
					(Date) result[3]// endDate
					
					);
			
			dtos.add(dto);
		}
		
		return dtos;
		
	}
	
}