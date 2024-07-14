package com.example.comeonBusan.controller;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.comeonBusan.dto.FestivalDto;
import com.example.comeonBusan.dto.HelpDto;
import com.example.comeonBusan.entity.Festival;
import com.example.comeonBusan.entity.Help;
import com.example.comeonBusan.entity.Likes;
import com.example.comeonBusan.repository.FestivalRepository;
import com.example.comeonBusan.repository.HelpRepository;
import com.example.comeonBusan.repository.LikeRepository;
import com.example.comeonBusan.service.FestivalService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/khj")
public class KhjController {

	@Autowired
	private FestivalRepository festivalRepository;

	@Autowired
	private HelpRepository helpRepository;
	
	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private FestivalService festivalService;
	
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

	// 파일 저장 장소
	private static final String UPLOAD_DIR = "C:/uploads/";
	
	// 파일 확장자 추출 메서드
	private String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
		
	}
	
	
	/*
	 * //@GetMapping("/festival") public ResponseEntity<String>
	 * receiveFestivalData(@RequestBody String xmlData) { try { // XML 데이터를 파싱하여
	 * Document 객체로 변환 DocumentBuilderFactory factory =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder builder =
	 * factory.newDocumentBuilder(); Document doc = builder.parse(new
	 * InputSource(new StringReader(xmlData)));
	 * 
	 * System.out.println(xmlData);
	 * 
	 * // XML 데이터에서 필요한 정보를 추출 NodeList items = doc.getElementsByTagName("item");
	 * for (int i = 0; i < items.getLength(); i++) { Festival festival = new
	 * Festival();
	 * festival.setMainTitle(doc.getElementsByTagName("MAIN_TITLE").item(i).
	 * getTextContent());
	 * festival.setAddr1(doc.getElementsByTagName("ADDR1").item(i).getTextContent())
	 * ;
	 * festival.setMainImgThumb(doc.getElementsByTagName("MAIN_IMG_THUMB").item(i).
	 * getTextContent());
	 * festival.setUcSeq(Long.parseLong(doc.getElementsByTagName("UC_SEQ").item(i).
	 * getTextContent()));
	 * 
	 * festival.setLng(Float.parseFloat(doc.getElementsByTagName("LNG").item(i).
	 * getTextContent()));
	 * festival.setMiddleSizeRm1(doc.getElementsByTagName("MIDDLE_SIZE_RM1").item(i)
	 * .getTextContent());
	 * festival.setUsageAmount(doc.getElementsByTagName("USAGE_AMOUNT").item(i).
	 * getTextContent());
	 * festival.setCntctTel(doc.getElementsByTagName("CNTCT_TEL").item(i).
	 * getTextContent());
	 * festival.setMainImgNormal(doc.getElementsByTagName("MAIN_IMG_NORMAL").item(i)
	 * .getTextContent());
	 * festival.setTrfcInfo(doc.getElementsByTagName("TRFC_INFO").item(i).
	 * getTextContent());
	 * festival.setItemCntnts(doc.getElementsByTagName("ITEMCNTNTS").item(i).
	 * getTextContent());
	 * 
	 * festival.setPlace(doc.getElementsByTagName("PLACE").item(i).getTextContent())
	 * ; festival.setSubtitle(doc.getElementsByTagName("SUBTITLE").item(i).
	 * getTextContent());
	 * festival.setUsageDay(doc.getElementsByTagName("USAGE_DAY").item(i).
	 * getTextContent());
	 * festival.setAddr2(doc.getElementsByTagName("ADDR2").item(i).getTextContent())
	 * ; festival.setUsageDayWeekAndTime(doc.getElementsByTagName(
	 * "USAGE_DAY_WEEK_AND_TIME").item(i).getTextContent());
	 * 
	 * festival.setGugunNm(doc.getElementsByTagName("GUGUN_NM").item(i).
	 * getTextContent());
	 * festival.setHomepageUrl(doc.getElementsByTagName("HOMEPAGE_URL").item(i).
	 * getTextContent());
	 * festival.setTitle(doc.getElementsByTagName("TITLE").item(i).getTextContent())
	 * ; festival.setMainPlace(doc.getElementsByTagName("MAIN_PLACE").item(i).
	 * getTextContent());
	 * festival.setLat(Float.parseFloat(doc.getElementsByTagName("LAT").item(i).
	 * getTextContent()));
	 * 
	 * // 데이터베이스에 저장 festivalRepository.save(festival);
	 * 
	 * // 데이터베이스에서 꺼내와서 리턴하기 }
	 * 
	 * // 성공 응답 return new ResponseEntity<>("XML data received and processed",
	 * HttpStatus.OK); } catch (Exception e) { // 오류 처리 e.printStackTrace(); return
	 * new ResponseEntity<>("Error processing XML data",
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	@GetMapping("/festival")
	public ResponseEntity<List<Festival>> getFestivalData() throws IOException, ParseException {

		System.out.println("getFestivalData................");

		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/6260000/FestivalService/getFestivalKr"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=gS%2Fgg5Okl%2BiENblgfTY4LW6GAxbDhZoC8eAjiF9qOHR3PiM%2FNwfq%2Bf5TxgaSO3onCSVo3mkUtZpVzPKS1kZyYg%3D%3D"); /*
																															 * Service
																															 * Key
																															 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("35", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("resultType", "UTF-8") + "="
				+ URLEncoder.encode("json", "UTF-8")); /* JSON방식으로 호출 시 파라미터 resultType=json 입력 */
		// urlBuilder.append("&" + URLEncoder.encode("UC_SEQ","UTF-8") + "=" +
		// URLEncoder.encode("", "UTF-8")); /*콘텐츠 ID*/
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
		// JSON 파일에서 날짜 데이터를 읽어오기
	    InputStream inputStream = new FileInputStream("src/main/resources/static/json/festival.json");
	    JsonNode dateRootNode = objectMapper.readTree(inputStream);
	    
	    
		for (JsonNode itemNode : itemsNode) {

			System.out.println(itemNode.path("LAT").asText());

			String latString = itemNode.path("LAT").asText();

			// 문자열을 double로 변환
			double latDouble = Festival.parseDoubleOrDefault(latString, 0.0);

			System.out.println("lat as double:" + latDouble);

			String lngString = itemNode.path("LNG").asText();

			double lngDouble = Festival.parseDoubleOrDefault(lngString, 0.0);
			
			String main_title = itemNode.path("MAIN_TITLE").asText();
			
			// 날짜 데이터를 JSON 파일에서 찾기
	        JsonNode matchingDateNode = null;
	        for (JsonNode dateNode : dateRootNode) {
	            if (dateNode.path("main_title").asText().equals(main_title)) {
	                matchingDateNode = dateNode;
	                break;
	            }
	        }

	        String s_startDate = matchingDateNode != null ? matchingDateNode.path("startDate").asText() : null;
	        String s_endDate = matchingDateNode != null ? matchingDateNode.path("endDate").asText() : null;
	        
	        Date startDate = null;
	        Date endDate = null;
	        
	        // 날짜 타입으로 바꾸기
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        if (s_startDate != null && !s_startDate.equals("2024-00-00")) {
	            startDate = formatter.parse(s_startDate);
	        }
	        
	        if (s_endDate != null && !s_endDate.equals("2024-00-00")) {
	            endDate = formatter.parse(s_endDate);
	        }
	        
	        System.out.println(startDate);
	        System.out.println(endDate);
			
			
			
			Festival entity = Festival.builder().ucSeq(Long.parseLong(itemNode.path("UC_SEQ").asText()))
					.mainTitle(itemNode.path("MAIN_TITLE").asText()).gugunNm(itemsNode.path("GUGUN_NM").asText())
					.lat(latDouble).lng(lngDouble).place(itemNode.path("PLACE").asText())
					.mainPlace(itemNode.path("MAIN_PLACE").asText()).title(itemNode.path("TITLE").asText())
					.subtitle(itemNode.path("SUBTITLE").asText()).addr1(itemNode.path("ADDR1").asText())
					.addr2(itemNode.path("ADDR2").asText()).cntctTel(itemNode.path("CNTCT_TEL").asText())
					.homepageUrl(itemNode.path("HOMEPAGE_URL").asText()).trfcInfo(itemNode.path("TRFC_INFO").asText())
					.usageDay(itemNode.path("USAGE_DAY").asText())
					.usageDayWeekAndTime(itemNode.path("USAGE_DAY_WEEK_AND_TIME").asText())
					.itemCntnts(itemNode.path("ITEMCNTNTS").asText())
					.middleSizeRm1(itemNode.path("MIDDLE_SIZE_RM1").asText())
					.usageAmount(itemNode.path("USAGE_AMOUNT").asText())
					.mainImgNormal(itemNode.path("MAIN_IMG_NORMAL").asText())
					.mainImgThumb(itemNode.path("MAIN_IMG_THUMB").asText())
					.startDate(startDate) // 추가
	                .endDate(endDate) // 추가
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

	@GetMapping("/noticeList")
	public List<Help> getNoticeList() {

		System.out.println("getNoticeList..............");

		List<Help> list = helpRepository.findAll();

		return list;

	}

	@GetMapping("/noticeDetail/{hnum}")
	public Help getNoticeDetail(@PathVariable("hnum") String hnum) {

		System.out.println("getNotieDetail............(rest Controller)");

		Long hnum_long = Long.parseLong(hnum);

		System.out.println("long타입으로 바꾼 hnum : " + hnum_long);

		Optional<Help> help = helpRepository.findById(hnum_long);

		Help realHelp = help.get();

		return realHelp;
	}

	@DeleteMapping("/noticeDelete/{hnum}")
	public String noticeDelete(@PathVariable("hnum") String hnum) {

		System.out.println("noticeDelete................(rest Controller)");

		Long hnum_long = Long.parseLong(hnum);
		try {
			helpRepository.deleteById(hnum_long);

			return "해당 공지가 성공적으로 삭제되었습니다";

		} catch (Exception e) {

			return "공지 삭제에 실패했습니다";
		}
	}

	//@PostMapping("/noticeAdd")
	public String noticeAdd(HelpDto helpDto) {

		System.out.println("파라미터로 받은 helpDto = " + helpDto);
		System.out.println("Current directory: " + System.getProperty("user.dir"));
		
		Help entity = new Help();

		entity.setTitle(helpDto.getTitle());
		entity.setContent(helpDto.getContent());
		entity.setRegDate(LocalDate.now());
		//entity.setHorigin_name(helpDto.getFileName());
		log.info(helpDto.getFileName());

		String newName = UUID.randomUUID().toString() + "_" + helpDto.getFileName();

		log.info(newName);

		//entity.setHnewName(newName);

		// 파일 저장
		File file = new File(newName);

		try {

			helpDto.getFile().transferTo(file);

			log.info("파일업로드 성공");

			// 썸네일 생성

			String thumbnailSaveName = "s_" + newName;
			//entity.setHthumbnailName(thumbnailSaveName);

			File thumbnailFile = new File(uploadPath + thumbnailSaveName); // 섬네일 파일 생성
			File ufile = new File(uploadPath + newName);

			// Thumbnailator.createThumbnail(file, thumbnailFile, 100, 100);
			Thumbnails.of(ufile).size(100, 100).toFile(thumbnailFile); // 섬네일 사이즈 100 100 설정
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("저장할 help = " + entity);
		helpRepository.save(entity);
		return "good";

	}
	
	@PostMapping("/noticeAdd")
	public String noticeAdd2(@RequestParam(value="file", required= false) MultipartFile file, @RequestParam("title") String title, @RequestParam("content") String content, HttpServletRequest request) {
		
			
		System.out.println("noticeAdd2..........................");
		System.out.println(title);
		System.out.println(content);
		System.out.println(file);
		String jwt = request.getHeader("Authorization");	
		System.out.println(jwt);
		Help help = new Help();
		
		try {
			
			String fileUrl = null;
			String thumbnailUrl = null;
			
			if(file != null && !file.isEmpty()) {
				
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				
				fileUrl = "http://localhost:9002/uploads/" + fileName;
				
				// 썸네일 생성 및 저장?
				String thumbnailFilename ="thumb_" + fileName;
				Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFilename);
				Files.copy(file.getInputStream(), thumbnailPath, StandardCopyOption.REPLACE_EXISTING);
				
				thumbnailUrl = "http://localhost:9002/uploads/" + thumbnailFilename;
		
			}
			
			if(jwt != null) {
				
				help.setTitle(title);
				help.setContent(content);
				help.setRegDate(LocalDate.now());
				
				if(fileUrl != null && thumbnailUrl != null) {
					
					help.setHimg(fileUrl);
					help.setHThumbnailImg(thumbnailUrl);
				}
				
				System.out.println("db에 저장할 공지 = " + help);
				helpRepository.save(help);				
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			return "공지가 성공적으로 등록되었습니다.";
	}
	
	@PostMapping("/doLikeFestival/{uc_seq}")
	public String doLikeFestival(@PathVariable("uc_seq") String uc_seq, HttpServletRequest request) {
		
		String likeToken = request.getHeader("Like" + uc_seq);
		
		System.out.println("doLike...............");
		
		Likes like = new Likes();
		Festival festival = new Festival();
		Long uc_seq_long = Long.parseLong(uc_seq);
		festival.setUcSeq(uc_seq_long);
		like.setFestival(festival);
		
		Optional<Likes> likes = likeRepository.findById(uc_seq_long);
		Likes fromDB = likes.get();
		
		like.setLikecount(fromDB.getLikecount() + 1);
		
		likeRepository.save(like);
		
		return "ok";
	}
	
	@GetMapping("/getFestival")
	public List<FestivalDto> getFestival() {
		
		//LocalDate today = LocalDate.now();		
		//System.out.println(today);
		//today.getMonthValue();
		//System.out.println(today.getMonthValue());
		
		List<FestivalDto> list = festivalService.getFestivalsStartingThisMonth();	
		
		for(FestivalDto dto : list) {
			
			System.out.println(dto.getPlace());
			System.out.println(dto.getMainImgThumb());
		}
		
		return list;
	}
	
}
