//package com.example.comeonBusan.service;
//
//import java.io.UnsupportedEncodingException;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.comeonBusan.dto.EmailMessage;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class EmailService {
//
//	private final RedisService redisService;
//
//	private final JavaMailSender javaMailSender;
//
//	@Value("${spring.mail.username}")
//	private String from;
//
//	public String makeUuid() {
//		
//		return UUID.randomUUID().toString();
//	}
//	
//	// 비밀번호 설정 링크 생성
//	String resetPasswordLink = "http:/localhost:8080/reset-password";
//	
//	@Transactional
//	public String sendResetPasswordEmail(String email) throws UnsupportedEncodingException {
//		String uuid = makeUuid();
//		String title = "[온나부산] 비밀번호 재설정"; // 이메일 제목
//		String content = "<p>안녕하세요,</p>" + 
//        "<p>비밀번호 재설정을 위해 다음 링크를 클릭해주세요:</p>" + 
//        "<p><a href=\"" + resetPasswordLink + "/" + uuid + "\">비밀번호 재설정 링크</a></p>" + "<br><br>"
//        + "해당 링크는 24시간 동안만 유효합니다." + "<br>"; // 이메일 내용 삽입
//        
//		EmailMessage emailMessage = new EmailMessage(email, title, content);
//		mailSend(emailMessage);
//        saveUuidAndEmail(uuid, email);
//		return uuid;
//	}
//
//	
//	public void mailSend(EmailMessage emailMessage) throws UnsupportedEncodingException {
//		MimeMessage mimeMessage = javaMailSender.createMimeMessage(); // MimeMessage 객체 생성
//		try {
//			// MimeMessageHelper를 사용하여 보다 쉽게 MimeMessage를 구성할 수 있다.
//			// true 값을 전달하면 multipart 형식의 메시지 전달이 가능. 문자 인코딩 설정도 가능하다.
//			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
//
//			// 이메일 수신자 설정
//			mimeMessageHelper.setTo(emailMessage.getTo());
//
//			// 이메일 제목 설정
//			mimeMessageHelper.setSubject(emailMessage.getSubject());
//
//			// 본문 내용 설정, false는 HTML 형식의 메세지를 사용하지 않음을 나타낸다.
//			mimeMessageHelper.setText(emailMessage.getMessage(), true);
//
//			// 이메일 발신자 설정
//			mimeMessageHelper.setFrom(new InternetAddress(from, "온나부산"));
//
//			// 이메일 보내기
//			javaMailSender.send(mimeMessage);
//
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	// UUID와 Email을 Redis에 저장
//	@Transactional
//	public void saveUuidAndEmail(String uuid, String email) {
//
//		long uuidValidTime = 60 * 60 * 24 * 1000L; // 24시간
//		redisService.setValuesWithTimeout(uuid, email, uuidValidTime); // key, value, timeout(millisecnds)
//
//	}
//
//}
