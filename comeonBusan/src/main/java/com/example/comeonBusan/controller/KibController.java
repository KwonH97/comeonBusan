package com.example.comeonBusan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.comeonBusan.entity.Food;
import com.example.comeonBusan.entity.TourList;
import com.example.comeonBusan.repository.FoodRepository;
import com.example.comeonBusan.repository.TourlistRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin("*")
@RequestMapping("/kibTest")
public class KibController {
	
	@Autowired
	TourlistRepository tourRepo;
	
	@Autowired
	FoodRepository foodRepo;
	
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
	
	//파일 저장 장소
	private static final String UPLOAD_DIR = "C:/uploads/";
	
	//파일 확장자 추출 메서드
	private String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}
	
	@PostMapping("/tour")
	public void registTour(@RequestParam(value="file", required= false) MultipartFile file,
            @RequestParam("uc_seq") String ucSeq,
            @RequestParam("maintitle") String mainTitle,
            @RequestParam("gugun_nm") String gugunNm,
            @RequestParam("lat") String lat,
            @RequestParam("lng") String lng,
            @RequestParam("place") String place,
            @RequestParam("title") String title,
            @RequestParam("subtitle") String subtitle,
            @RequestParam("addr") String addr,
            @RequestParam("tel") String tel,
            @RequestParam("homepage_url") String homepageUrl,
            @RequestParam("trfc_info") String trfcInfo,
            @RequestParam("usage_day") String usageDay,
            @RequestParam("hldy_info") String hldyInfo,
            @RequestParam("useageDay_week_and_time") String useageDayWeekAndTime,
            @RequestParam("usage_amount") String usageAmount,
            @RequestParam("middle_size_rm") String middleSizeRm,
            @RequestParam("itemcntnts") String itemcntnts,
            HttpServletRequest request) {
		
		String jwt = request.getHeader("Autorization");
		
		TourList tourEn = new TourList();
		
		try {
			String fileUrl = null;
			String thumbnailUrl = null;
			
			if(file != null && !file.isEmpty()) {
				/*
				//UUID를 사용해 고유 파일 이름 생성
				String originalFilename = file.getOriginalFilename();
				String fileExtension = getFileExtension(originalFilename);
				String uniqueFilename = UUID.randomUUID().toString() + "." + fileExtension;
				
				//원본 파일 저장
				File originalFile = new File(UPLOAD_DIR + uniqueFilename);
				file.transferTo(originalFile);
				*/
				
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				
				fileUrl = "http://localhost:9002/uploads/" + fileName;
				
				/*
				//썸네일 생성 및 저장
				String thumbnailFilename = "thumb_" + uniqueFilename;
				File thumbnailFile = new File(UPLOAD_DIR + thumbnailFilename);
				Thumbnails.of(originalFile).size(100, 100).toFile(thumbnailFile);
				*/
				
				String thumbnailFilename = "thumb_" + fileName;
				Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFilename);
				//File thumbnailFile = thumbnailPath.toFile();
				Files.copy(file.getInputStream(),thumbnailPath , StandardCopyOption.REPLACE_EXISTING);
				
				//Thumbnails.of(targetLocation.toFile()).size(100, 100).toFile(thumbnailFile);
				
				thumbnailUrl = "http://localhost:9002/uploads/" + thumbnailFilename;
			}
			
			if(jwt == null) {
				tourEn.setUc_seq(ucSeq);
	            tourEn.setMaintitle(mainTitle);
	            tourEn.setGugun_nm(gugunNm);
	            tourEn.setLat(lat);
	            tourEn.setLng(lng);
	            tourEn.setPlace(place);
	            tourEn.setTitle(title);
	            tourEn.setSubtitle(subtitle);
	            tourEn.setAddr(addr);
	            tourEn.setTel(tel);
	            tourEn.setHomepage_url(homepageUrl);
	            tourEn.setTrfc_info(trfcInfo);
	            tourEn.setUsage_day(usageDay);
	            tourEn.setHldy_info(hldyInfo);
	            tourEn.setUseageDay_week_and_time(useageDayWeekAndTime);
	            tourEn.setUsage_amount(usageAmount);
	            tourEn.setMiddle_size_rm(middleSizeRm);
	            tourEn.setITEMCNTNTS(itemcntnts);
	            
	            if(fileUrl != null && thumbnailUrl != null) {
	            	tourEn.setMain_img_normal(fileUrl);
		            tourEn.setMain_img_thumb(thumbnailUrl);
	            }
	            
	            System.out.println(tourEn);
	            tourRepo.save(tourEn);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/food")
	public ResponseEntity<List<Food>> foodTourList() throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/FoodService/getFoodKr"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=UzRFDzI6IWxs4ali8ZzhS%2FNQFzOPHO%2FLYOSFX6EItT5fnEy%2BmUS8Pa7%2Fmb%2B0ESkJizJdsWY4oHd6CiwCtju%2BEg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("398", "UTF-8")); /*한 페이지 결과 수*/
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
        JsonNode itemsNode = rootNode.path("getFoodKr").path("item");
        
        List<Food> entities = new ArrayList<>();
        
        for (JsonNode itemNode : itemsNode) {
            Food entity = Food.builder()
            		.UC_SEQ(itemNode.path("UC_SEQ").asText())
                    .main_title(itemNode.path("MAIN_TITLE").asText())
                    .gugun_nm(itemNode.path("GUGUN_NM").asText())
                    .lat(itemNode.path("LAT").asText())
                    .lng(itemNode.path("LNG").asText())
                    .place(itemNode.path("PLACE").asText())
                    .title(itemNode.path("TITLE").asText())
                    .subtitle(itemNode.path("SUBTITLE").asText())
                    .addr1(itemNode.path("ADDR1").asText())
                    .addr2(itemNode.path("ADDR2").asText())
                    .cntct_tel(itemNode.path("CNTCT_TEL").asText())
                    .homepage_url(itemNode.path("HOMEPAGE_URL").asText())
                    .usage_day_week_and_time(itemNode.path("USAGE_DAY_WEEK_AND_TIME").asText())
                    .rprsntv_menu(itemNode.path("RPRSNTV_MENU").asText())
            		.main_img_normal(itemNode.path("MAIN_IMG_NORMAL").asText())
            		.main_img_thumb(itemNode.path("MAIN_IMG_THUMB").asText())
            		.itemcntnts(itemNode.path("ITEMCNTNTS").asText())
                    .build();

            entities.add(entity); 
            
        }
        
        // 데이터베이스에 저장
        foodRepo.saveAll(entities);
        
        List<Food> foodlist = foodRepo.findAll();

        return ResponseEntity.ok(foodlist);
        
    }
	
	@GetMapping("/food/{uc_seq}")
	public Food foodDetail(@PathVariable("uc_seq") String uc_seq) {
		Optional<Food> result= foodRepo.findById(uc_seq);
		Food food = result.get();
		
		return food;
	}
	
}
