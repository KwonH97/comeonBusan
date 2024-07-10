package com.example.comeonBusan.controller;

import java.io.StringReader;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.IOException;


import com.example.comeonBusan.entity.Festival;
import com.example.comeonBusan.repository.FestivalRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/khj")
public class KhjController {

	 @Autowired
	 private FestivalRepository festivalRepository;

/*	    //@GetMapping("/festival")
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
*/
	 
	    @GetMapping("/festival")
	    public ResponseEntity<List<Festival>> getFestivalData() throws IOException {
	    	
	    	System.out.println("getFestivalData................");
	    	
	    	 StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/FestivalService/getFestivalKr"); /*URL*/
	         urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=gS%2Fgg5Okl%2BiENblgfTY4LW6GAxbDhZoC8eAjiF9qOHR3PiM%2FNwfq%2Bf5TxgaSO3onCSVo3mkUtZpVzPKS1kZyYg%3D%3D"); /*Service Key*/
	         urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	         urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("35", "UTF-8")); /*한 페이지 결과 수*/
	         urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*JSON방식으로 호출 시 파라미터 resultType=json 입력*/
	         //urlBuilder.append("&" + URLEncoder.encode("UC_SEQ","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*콘텐츠 ID*/
	         URL url = new URL(urlBuilder.toString());
	         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         conn.setRequestProperty("Content-type", "application/json");
	         System.out.println("Response code: " + conn.getResponseCode());
	         BufferedReader rd;
	         if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	             rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         } else {
	             rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	         }
	         StringBuilder sb = new StringBuilder();
	         String line;
	         while ((line = rd.readLine()) != null) {
	             sb.append(line);
	         }
	         rd.close();
	         conn.disconnect();
	         System.out.println(sb.toString());
	         
	         String jsonResponse = sb.toString();
	         
	         // JSON 파싱 + DB 저장
	         
	         ObjectMapper objectMapper = new ObjectMapper();
	         JsonNode rootNode = objectMapper.readTree(jsonResponse);
	         JsonNode itemsNode = rootNode.path("getFestivalKr").path("item");
	         
	         List<Festival> entities = new ArrayList<>();
	         
	         for (JsonNode itemNode : itemsNode) {
	        	 
	        	 
	        	 System.out.println(itemNode.path("LAT").asText());
	        	 
	        	 String latString = itemNode.path("LAT").asText();
	        	 
	        	// 문자열을 double로 변환
	             double latDouble = Festival.parseDoubleOrDefault(latString, 0.0);
	        	 
	        	 System.out.println("lat as double:" + latDouble);
	        	 
	        	 
	        	 String lngString = itemNode.path("LNG").asText();
	        	 
	        	 double lngDouble = Festival.parseDoubleOrDefault(lngString, 0.0);
	        	 
	        	 
	        	 
	        	 
	        	 Festival entity = Festival.builder()
	        			 .ucSeq(Long.parseLong(itemNode.path("UC_SEQ").asText()))
	        			 .mainTitle(itemNode.path("MAIN_TITLE").asText())
	        			 .gugunNm(itemsNode.path("GUGUN_NM").asText())
	        			 .lat(latDouble)
	        			 .lng(lngDouble)
	        			 .place(itemNode.path("PLACE").asText())
	        			 .mainPlace(itemNode.path("MAIN_PLACE").asText())
	        			 .title(itemNode.path("TITLE").asText())
	        			 .subtitle(itemNode.path("SUBTITLE").asText())
	        			 .addr1(itemNode.path("ADDR1").asText())
	        			 .addr2(itemNode.path("ADDR2").asText())
	        			 .cntctTel(itemNode.path("CNTCT_TEL").asText())
	        			 .homepageUrl(itemNode.path("HOMEPAGE_URL").asText())
	        			 .trfcInfo(itemNode.path("TRFC_INFO").asText())
	        			 .usageDay(itemNode.path("USAGE_DAY").asText())
	        			 .usageDayWeekAndTime(itemNode.path("USAGE_DAY_WEEK_AND_TIME").asText())
	        			 .itemCntnts(itemNode.path("ITEMCNTNTS").asText())
	        			 .middleSizeRm1(itemNode.path("MIDDLE_SIZE_RM1").asText())
	        			 .usageAmount(itemNode.path("USAGE_AMOUNT").asText())
	        			 .mainImgNormal(itemNode.path("MAIN_IMG_NORMAL").asText())
	        			 .mainImgThumb(itemNode.path("MAIN_IMG_THUMB").asText())
	        			 .build();
	        	 
	        	 entities.add(entity);
	         }
	         
	         
	         festivalRepository.saveAll(entities);
	         
	         List<Festival> festivalList = festivalRepository.findAll();
	         
	         return ResponseEntity.ok(festivalList);
	    	
	    }
	    
	    @GetMapping("/festival2/{uc_seq}")
	    public Festival getFestivalDetail(@PathVariable("uc_seq") String uc_seq) {
	    	
	    	System.out.println("getFestivalDetail...........");
	    	
	    	Long uc_seq_long = Long.parseLong(uc_seq);
	    	
	    	System.out.println(uc_seq_long);
	    	
	    	Optional<Festival> result = festivalRepository.findById(uc_seq_long);
	    	Festival fes = result.get();
	    	
	    	System.out.println(fes);
	    	
	    	return fes;
	    	
	    	
	    }
	    
	    
}
	

