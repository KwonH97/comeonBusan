package com.example.comeonBusan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.entity.Views;
import com.example.comeonBusan.repository.ViewsRepository;

@RestController
@RequestMapping("/chart")
public class ChartController {

	@Autowired
	private ViewsRepository viewsRepository;

	@GetMapping("/getChartData")
	public ResponseEntity<Object> getChartData() {

		List<Views> list = viewsRepository.findAll();

		if (!list.isEmpty()) {
			List<List<String>> responseList = new ArrayList<>();
			
			for(int i = 0; i < list.size(); i++) {
				
				List<String> info = new ArrayList<>();
				
				Views result = list.get(i);
				Date regdate =  result.getRegdate();
				Long viewCount = result.getViewcount();
				
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String str = format.format(regdate);
				String realviewcount = String.valueOf(viewCount);
				
				info.add(str);
				info.add(realviewcount);
				
				// System.out.println(info);
			
				responseList.add(info);
				
			//	Date regdate = firstView.getRegdate();
			//	System.out.println("Registration Date: " + regdate);
			//	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//	String str = format.format(regdate);
			//	System.out.println(str);
			
				
				
			// regDate에서 일자를 뽑아서
			// 일자가 동일한 데이터끼리 view count 수를 합한다.
			// 지금 있는 데이터로는 7월 26일 데이터가 총 60개
			// viewcount를 60으로 해서 넘긴다.
			// 합쳐서 넘겨도 되고 넘겨서 자바스크립트에서 합쳐도 된다.	
				
			}
			
			System.out.println(responseList);
			
			return ResponseEntity.ok(responseList);
			
		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회수 데이터를 찾을 수 없습니다.");

		}
		
	}
}
