package com.example.comeonBusan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.entity.TourList;
import com.example.comeonBusan.repository.TourlistRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@CrossOrigin("*")
@RequestMapping("/kibTest")
public class KibController {
	
	@Autowired
	TourlistRepository tourRepo;
	
	
	@GetMapping("/tourList")
	public ResponseEntity<List<TourList>> getTourList() throws IOException{
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/AttractionService/getAttractionKr"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=UzRFDzI6IWxs4ali8ZzhS%2FNQFzOPHO%2FLYOSFX6EItT5fnEy%2BmUS8Pa7%2Fmb%2B0ESkJizJdsWY4oHd6CiwCtju%2BEg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("146", "UTF-8")); /*한 페이지 결과 수*/
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
        
        // JSON 파싱 및 데이터베이스 저장
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode itemsNode = rootNode.path("getAttractionKr").path("item");

        List<TourList> entities = new ArrayList<>();

        for (JsonNode itemNode : itemsNode) {
            TourList entity = TourList.builder()
            		.uc_seq(itemNode.path("UC_SEQ").asText())
                    .maintitle(itemNode.path("MAIN_TITLE").asText())
                    .gugun_nm(itemNode.path("GUGUN_NM").asText())
                    .lat(itemNode.path("LAT").asText())
                    .lng(itemNode.path("LNG").asText())
                    .place(itemNode.path("PLACE").asText())
                    .title(itemNode.path("TITLE").asText())
                    .subtitle(itemNode.path("SUBTITLE").asText())
                    .addr(itemNode.path("ADDR1").asText())
                    .tel(itemNode.path("CNTCT_TEL").asText())
                    .homepage_url(itemNode.path("HOMEPAGE_URL").asText())
                    .trfc_info(itemNode.path("TRFC_INFO").asText())
                    .usage_day(itemNode.path("USAGE_DAY").asText())
                    .hldy_info(itemNode.path("HLDY_INFO").asText())
                    .useageDay_week_and_time(itemNode.path("USAGE_DAY_WEEK_AND_TIME").asText())
                    .usage_amount(itemNode.path("USAGE_AMOUNT").asText())
                    .middle_size_rm(itemNode.path("MIDDLE_SIZE_RM1").asText())
                    .main_img_normal(itemNode.path("MAIN_IMG_NORMAL").asText())
                    .main_img_thumb(itemNode.path("MAIN_IMG_THUMB").asText())
                    .ITEMCNTNTS(itemNode.path("ITEMCNTNTS").asText())
                    .build();

            entities.add(entity);
        }

        // 데이터베이스에 저장
        tourRepo.saveAll(entities);
        
        List<TourList> tourlist = tourRepo.findAll();

        return ResponseEntity.ok(tourlist);
	}
	
	@GetMapping("/tour/{uc_seq}")
	public TourList getTourDetail(@PathVariable("uc_seq")String uc_seq) {
		
		Optional<TourList> result= tourRepo.findById(uc_seq);
		TourList tour= result.get();
		
		System.out.println(tour);
		
		return tour;
	}
	
	@PostMapping("/tour")
	public void registTour() {
		
	}
	
}
