//package com.example.comeonBusan.service;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import lombok.RequiredArgsConstructor;
//
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class RedisService {
//
//	private final RedisTemplate<String, String> redisTemplate;
//	
//	@Transactional
//	public void setValues(String key, String value) {
//		redisTemplate.opsForValue().set(key, value);
//		
//	}
//	
//	// 만료 시간 설정 -> 자동 삭제
//	@Transactional
//	public void setValuesWithTimeout(String )
//}
