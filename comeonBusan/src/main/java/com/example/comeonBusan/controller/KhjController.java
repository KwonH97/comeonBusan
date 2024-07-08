package com.example.comeonBusan.controller;

import java.io.StringReader;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.example.comeonBusan.entity.Festival;
import com.example.comeonBusan.repository.FestivalRepository;

@RestController
//@CrossOrigin("*")
@RequestMapping("/khj")
public class KhjController {

	 @Autowired
	    private FestivalRepository festivalRepository;

	    @PostMapping("/festival")
	    public ResponseEntity<String> receiveFestivalData(@RequestBody String xmlData) {
	        try {
	            // XML 데이터를 파싱하여 Document 객체로 변환
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document doc = builder.parse(new InputSource(new StringReader(xmlData)));
	            
	            System.out.println(xmlData);
	            
	            // XML 데이터에서 필요한 정보를 추출
	            NodeList items = doc.getElementsByTagName("item");
	            for (int i = 0; i < items.getLength(); i++) {
	                Festival festival = new Festival();
	                festival.setMainTitle(doc.getElementsByTagName("MAIN_TITLE").item(i).getTextContent());
	                festival.setAddr1(doc.getElementsByTagName("ADDR1").item(i).getTextContent());
	                festival.setMainImgThumb(doc.getElementsByTagName("MAIN_IMG_THUMB").item(i).getTextContent());
	                festival.setUcSeq(Long.parseLong(doc.getElementsByTagName("UC_SEQ").item(i).getTextContent()));

	                festival.setLng(Float.parseFloat(doc.getElementsByTagName("LNG").item(i).getTextContent()));
	                festival.setMiddleSizeRm1(doc.getElementsByTagName("MIDDLE_SIZE_RM1").item(i).getTextContent());
	                festival.setUsageAmount(doc.getElementsByTagName("USAGE_AMOUNT").item(i).getTextContent());
	                festival.setCntctTel(doc.getElementsByTagName("CNTCT_TEL").item(i).getTextContent());
	                festival.setMainImgNormal(doc.getElementsByTagName("MAIN_IMG_NORMAL").item(i).getTextContent());
	                festival.setTrfcInfo(doc.getElementsByTagName("TRFC_INFO").item(i).getTextContent());
	                festival.setItemCntnts(doc.getElementsByTagName("ITEMCNTNTS").item(i).getTextContent());
	                
	                festival.setPlace(doc.getElementsByTagName("PLACE").item(i).getTextContent());
	                festival.setSubtitle(doc.getElementsByTagName("SUBTITLE").item(i).getTextContent());
	                festival.setUsageDay(doc.getElementsByTagName("USAGE_DAY").item(i).getTextContent());
	                festival.setAddr2(doc.getElementsByTagName("ADDR2").item(i).getTextContent());
	                festival.setUsageDayWeekAndTime(doc.getElementsByTagName("USAGE_DAY_WEEK_AND_TIME").item(i).getTextContent());
	                
	                festival.setGugunNm(doc.getElementsByTagName("GUGUN_NM").item(i).getTextContent());
	                festival.setHomepageUrl(doc.getElementsByTagName("HOMEPAGE_URL").item(i).getTextContent());
	                festival.setTitle(doc.getElementsByTagName("TITLE").item(i).getTextContent());
	                festival.setMainPlace(doc.getElementsByTagName("MAIN_PLACE").item(i).getTextContent());
	                festival.setLat(Float.parseFloat(doc.getElementsByTagName("LAT").item(i).getTextContent()));
	                
	                // 데이터베이스에 저장
	                festivalRepository.save(festival);
	                
	                // 데이터베이스에서 꺼내와서 리턴하기
	            }

	            // 성공 응답
	            return new ResponseEntity<>("XML data received and processed", HttpStatus.OK);
	        } catch (Exception e) {
	            // 오류 처리
	            e.printStackTrace();
	            return new ResponseEntity<>("Error processing XML data", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
}
	

