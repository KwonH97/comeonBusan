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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.comeonBusan.entity.Food;
import com.example.comeonBusan.repository.FoodRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import net.coobird.thumbnailator.Thumbnails;

@RestController
@RequestMapping("/kibTest")
public class FoodController {

	@Autowired
	FoodRepository foodRepo;
	
	//파일 저장 장소
	private static final String UPLOAD_DIR = "C:/uploads/";
	
	//파일 확장자 추출 메서드
	private String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}
	
	private static final String UPLOADED_FOLDER = "src/main/resources/static/images/";
	
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
        
        List<Food> list = foodRepo.findAll();
        
        
        if (list != null && !list.isEmpty()) {

			return ResponseEntity.ok(list);

		} else {

			foodRepo.saveAll(entities);

			List<Food> foodList = foodRepo.findAll();

			return ResponseEntity.ok(foodList);

		}

        
        
    }
	
	@GetMapping("/food/{uc_seq}")
	public Food foodDetail(@PathVariable("uc_seq") String uc_seq) {
		Optional<Food> result= foodRepo.findById(uc_seq);
		Food food = result.get();
		
		return food;
	}
		
	@PostMapping("/food")
	public void regiFoodTour(@RequestParam(value="file", required= false) MultipartFile file,
			@RequestParam("uc_seq") String uc_seq,
			@RequestParam("title") String title,
			@RequestParam("subtitle") String subtitle,
			@RequestParam("rprsntv_menu") String rprsntv_menu,
			@RequestParam("gugun_nm") String gugun_nm,
			@RequestParam("addr1") String addr1,
			@RequestParam("cntct_tel") String cntct_tel,
			@RequestParam("homepage_url") String homepage_url,
			@RequestParam("usage_day_week_and_time") String usage_day_week_and_time,
			@RequestParam("itemcntnts") String itemcntnts,
			HttpServletRequest request) {
		
		System.out.println("등록기능 실행.......");
		
		String jwt = request.getHeader("Autorization");
		
		//db 저장용 엔티티 (dto생성해서도 사용가능..) 
		Food food = new Food();
		
		try{
			String fileUrl = null;
			String thumbnailUrl = null;
			
			if(file != null && !file.isEmpty()) {
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				
				fileUrl = "http://localhost:9002/uploads/" + fileName;
				
				String thumbnailFilename = "thumb_" + fileName;
				Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFilename);
				//Files.copy(file.getInputStream(),thumbnailPath , StandardCopyOption.REPLACE_EXISTING);
				Thumbnails.of(file.getInputStream()).size(200, 200).toFile(thumbnailPath.toFile());
				
				thumbnailUrl = "http://localhost:9002/uploads/" + thumbnailFilename;
				
			}
			
			String place = title;
			String title2 = title;
			
			if(jwt == null) {
				food.setUC_SEQ(uc_seq);
				food.setMain_title(title);
				food.setPlace(place);
				food.setTitle(title2);
				food.setSubtitle(subtitle);
				food.setRprsntv_menu(rprsntv_menu);
				food.setGugun_nm(gugun_nm);
				food.setAddr1(addr1);
				food.setCntct_tel(cntct_tel);
				food.setHomepage_url(homepage_url);
				food.setUsage_day_week_and_time(usage_day_week_and_time);
				food.setItemcntnts(itemcntnts);
				
				
				if(fileUrl != null && thumbnailUrl != null) {
					food.setMain_img_normal(fileUrl);
					food.setMain_img_thumb(thumbnailUrl);
				}
				
				System.out.println("save: " + food);
				
				foodRepo.save(food);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/mfood")
	public void modifyFoodtour(@RequestParam(value="file", required= false) MultipartFile file,
			@RequestParam("uc_seq") String uc_seq,
			@RequestParam("title") String title,
			@RequestParam("subtitle") String subtitle,
			@RequestParam("rprsntv_menu") String rprsntv_menu,
			@RequestParam("gugun_nm") String gugun_nm,
			@RequestParam("addr1") String addr1,
			@RequestParam("cntct_tel") String cntct_tel,
			@RequestParam("homepage_url") String homepage_url,
			@RequestParam("usage_day_week_and_time") String usage_day_week_and_time,
			@RequestParam("itemcntnts") String itemcntnts,
			HttpServletRequest request) {
		
		String jwt = request.getHeader("Authorization");
		
		Optional<Food> result = foodRepo.findById(uc_seq);
		if(result.isPresent()) {
			Food food = result.get();
			
			try{
				String fileUrl = null;
				String thumbnailUrl = null;
				
				if(file != null && !file.isEmpty()) {
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
					Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
					
					fileUrl = "http://localhost:9002/uploads/" + fileName;
					
					String thumbnailFilename = "thumb_" + fileName;
					Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFilename);
					Files.copy(file.getInputStream(),thumbnailPath , StandardCopyOption.REPLACE_EXISTING);
					
					thumbnailUrl = "http://localhost:9002/uploads/" + thumbnailFilename;
					
					// 이미지 URL 업데이트
	                food.setMain_img_normal(fileUrl);
	                food.setMain_img_thumb(thumbnailUrl);
				}
				
				// 다른 필드 업데이트
	            food.setMain_title(title);
	            food.setSubtitle(subtitle);
	            food.setRprsntv_menu(rprsntv_menu);
	            food.setGugun_nm(gugun_nm);
	            food.setAddr1(addr1);
	            food.setCntct_tel(cntct_tel);
	            food.setHomepage_url(homepage_url);
	            food.setUsage_day_week_and_time(usage_day_week_and_time);
	            food.setItemcntnts(itemcntnts);

	            foodRepo.save(food);
	            
	            System.out.println("업데이트 확인: " + food);
	            
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
