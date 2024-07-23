package com.example.comeonBusan.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.comeonBusan.dto.FestivalUpdateDto;
import com.example.comeonBusan.entity.Festival;
import com.example.comeonBusan.repository.FestivalRepository;
import com.example.comeonBusan.service.FestivalService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@RequestMapping("/khj")
@RestController
@Slf4j
public class FestivalController {

	@Autowired
	private FestivalRepository festivalRepository;

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

			//System.out.println(itemNode.path("LAT").asText());

			String latString = itemNode.path("LAT").asText();

			// 문자열을 double로 변환
			double latDouble = Festival.parseDoubleOrDefault(latString, 0.0);

			//System.out.println("lat as double:" + latDouble);

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

			//System.out.println(startDate);
			//System.out.println(endDate);

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
					.mainImgThumb(itemNode.path("MAIN_IMG_THUMB").asText()).startDate(startDate) // 추가
					.endDate(endDate) // 추가
					.build();

			entities.add(entity);
		}
		
		Festival additionalFestival = Festival.builder()
				.ucSeq(450L)
				.mainTitle("노릇노릇 부산(한,영,중간,중번,일)")
				.gugunNm("부산 동래구")
				.lat(35.2050)
				.lng(129.0795)
				.place("노릇노릇 부산")
				.mainPlace("노릇노릇 부산")
				.title("국립민속박물관 2024 k-museums 공동기획전, '노릇노릇 부산'")
				.subtitle("부산시어 고등어와 부산의 해양민속문화에 대한 전시")
		        .addr1("부산 동래구 우장춘로 175")
		        .addr2("")
		        .cntctTel("051-550-8824")
		        .homepageUrl("https://www.busan.go.kr/sea/onnews/1630246")
		        .trfcInfo("부산해양자연사박물관 앞 하차")
		        .usageDay("2024-06-25 ~ 2024-12-01")
		        .usageDayWeekAndTime("화~일 09:00~18:00, 월요일 휴관")
		        .itemCntnts("이번 전시는 박물관이 지난해 12월 국립민속박물관의 ‘k-museums 공모’에 선정돼 국비를 지원받아 공동 기획한 전시입니다.\r\n"
		        		+ "\r\n"
		        		+ "부산시어(市魚) 고등어와 부산의 해양수산문화를 쉽고 재미있게 알리고자 기획하였습니다.\r\n"
		        		+ "\r\n"
		        		+ "6월 24일 오후 2시 개막식을 시작으로 6월 25일부터 12월 1일까지 138일간 개최됩니다.\r\n"
		        		+ "\r\n"
		        		+ "특히, 고등어의 생물학적·영양학적 특징뿐만 아니라 잊혀가는 고갈비 문화와 부산만의 독특한 해양수산문화를 즐길 수 있도록 국·공립박물관의 대여유물 및 박물관 소장 표본 100여 점을 선보일 예정입니다.\r\n"
		        		+ "\r\n"
		        		+ "\r\n"
		        		+ "\r\n"
		        		+ "\r\n"
		        		+ "\r\n"
		        		+ "전시는 ▲1부 <부며들다> ▲2부 <고며들다>, 총 2부로 구성되었습니다.\r\n"
		        		+ "\r\n"
		        		+ " -1부 <부며들다 –파닥파닥 고등어>에서는 고등어가 부산에 스며든 과정을 유물과 영상매체 등을 통해 살펴봅니다.\r\n"
		        		+ "\r\n"
		        		+ "특히 부산공동어시장과 자갈치시장 등 부산이 고등어 유통의 중심지가 된 과정을 살펴보고, 시어로 지정된 이유를 알기 쉽게 설명합니다.\r\n"
		        		+ "\r\n"
		        		+ " - 2부 <고며들다 –노릇노릇 고갈비>에서는 고등어가 부산에 스며들었듯 부산 사람들이 고등어에 스며드는 과정을 유물과 재현품, 시·청각 자료 등을 통해 살펴봅니다.\r\n"
		        		+ "\r\n"
		        		+ "특히 7080 광복동의 고갈비골목을 재현해 그 시대의 향수를 불러일으키고 고등어가 밥상 위에 오르기까지의 과정, 고등어 요리 조리법, 맛집 지도 등\r\n"
		        		+ "\r\n"
		        		+ "      고등어가 국민 생선이 될 수 있었던 이유를 설명합니다. \r\n"
		        		+ "\r\n"
		        		+ "\r\n"
		        		+ "\r\n"
		        		+ " 이외에도, 고등어와 관련한 다양한 체험을 할 수 있는 \r\n"
		        		+ "\r\n"
		        		+ "  ▲엘피(LP)판·씨디(CD)플레이어를 통해 고등어 관련 노래를 듣고 ▲고등어 요리 체험과 함께 \r\n"
		        		+ "\r\n"
		        		+ "  ▲고등어 관련 책을 읽어보고 ▲고등어에게 하고 싶은 말을 글로 전할 수 있습니다.\r\n"
		        		+ "\r\n"
		        		+ "\r\n"
		        		+ "\r\n"
		        		+ "이번 전시를 통해 부산 시민들이 부산시어 고등어와 고등어에 얽힌 부산의 독특한 해양수산문화를 재인식하는 계기가 되길 바라겠습니다.")
		        .middleSizeRm1("장애인 화장실, 장애인 주차구역, 휠체어접근 가능")
		        .usageAmount("무료")
		        .mainImgNormal("http://localhost:9002/uploads/노릇노릇 부산_웹배너.jpg")
		        .mainImgThumb("http://localhost:9002/uploads/thumb_노릇노릇 부산_웹배너.jpg")
		        .startDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-06-25"))
		        .endDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-12-01"))
		        .build();
		
		entities.add(additionalFestival);
		
		List<Festival> list = festivalRepository.findAll();
		//System.out.println(list);
		if (list != null && !list.isEmpty()) {

			return ResponseEntity.ok(list);

		} else {

			festivalRepository.saveAll(entities);

			List<Festival> festivalList = festivalRepository.findAll();

			return ResponseEntity.ok(festivalList);

		}

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

	@PostMapping("/festivalAdd")
	public String festivalAdd(@RequestPart(value = "file", required = false) MultipartFile file, HttpServletRequest request, @RequestPart("festival") FestivalUpdateDto festDto) throws ParseException {
		
		
		System.out.println("festivalAdd...................");
		System.out.println(festDto);
		System.out.println(file);
		
		String jwt = request.getHeader("Authorization");
		
		System.out.println(jwt);
		System.out.println(festDto.getStartDate());
		System.out.println(festDto.getEndDate());
		
		Festival festival = new Festival();
		
		try {
			
			String fileUrl = null;
			String thumbnailUrl = null;
			
			if(file != null && !file.isEmpty()) { // 첨부 파일이 있을 경우에만 이미지 url 등록
				
				// 파일 처리 로직 추가
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
				fileUrl = "http://localhost:9002/uploads/" + fileName;
				
				// 썸네일 생성 및 저장
				String thumbnailFilename = "thumb_" + fileName;
				Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFilename);
				Thumbnails.of(file.getInputStream()).size(200, 200).toFile(thumbnailPath.toFile());
				
				thumbnailUrl = "http://localhost:9002/uploads/" + thumbnailFilename;
			}else {
				
				return "이미지 파일을 첨부해주세요";
			}
			
			
			Date startDate = null;
			Date endDate = null;
			
			// 날짜 타입으로 바꾸기
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			if(festDto.getStartDate() != null && !festDto.getStartDate().isEmpty()) {
				String s_startDate = festDto.getStartDate();
				startDate = formatter.parse(s_startDate);	
			} else if (festDto.getStartDate() == null && festDto.getStartDate().isEmpty()) {
				startDate = null;
			}
			
			if(festDto.getEndDate() != null && !festDto.getStartDate().isEmpty()) {
				
				String s_endDate = festDto.getEndDate();
				endDate = formatter.parse(s_endDate);
			}else if (festDto.getEndDate() == null && festDto.getEndDate().isEmpty()) {
				
				endDate = null;
			}
			
			System.out.println(startDate);
			System.out.println(endDate);
			
			// 관리자가 입력한 ucSeq가 존재할 경우 등록거부
	/*		Optional<Festival> list = festivalRepository.findById(festDto.getUcSeq());
			Festival result = list.get();
			
			System.out.println("관리자가 입력한 ucSeq로 데이터 조회 : " + result);
			
			if(result != null) {
				
				return "이미 존재하는 축제번호입니다. 새로운 등로번호를 입력해주세요";
			}
			
		*/	
			if(jwt != null) {
				
				Festival entity = new Festival();
				
				entity.setUcSeq(festDto.getUcSeq());
				entity.setMainTitle(festDto.getMaintitle());
				entity.setTitle(festDto.getTitle());
				entity.setSubtitle(festDto.getSubtitle());
				entity.setMainPlace(festDto.getMainPlace());
				entity.setPlace(festDto.getPlace());
				entity.setItemCntnts(festDto.getItemcntnts());
				entity.setAddr1(festDto.getAddr1());
				entity.setAddr2(festDto.getAddr2());
				entity.setGugunNm(festDto.getGugunNm());
				entity.setCntctTel(festDto.getCntctTel());
				entity.setHomepageUrl(festDto.getHomepageUrl());
				entity.setUsageDayWeekAndTime(festDto.getUsageDayWeekAndTime());
				entity.setStartDate(startDate);
				entity.setEndDate(endDate);
				entity.setUsageDay(festDto.getUsageDay());
				entity.setUsageAmount(festDto.getUsageAmount());
				entity.setMiddleSizeRm1(festDto.getMiddleSizeRm1());
				entity.setTrfcInfo(festDto.getTrfcInfo());
				entity.setLat(Double.parseDouble(festDto.getLat()));
				entity.setLng(Double.parseDouble(festDto.getLng()));

				
				 if (fileUrl != null && thumbnailUrl != null) {
				 
				 entity.setMainImgNormal(fileUrl); 
				 entity.setMainImgThumb(thumbnailUrl); 
				 
				 }

					System.out.println("DB에 저장할 축제 엔티티 = " + entity);
					festivalRepository.save(entity);

				}

			} catch (IOException e) {

				e.printStackTrace();
			}
			return "축제 정보가 성공적으로 등록되었습니다..";
		}
	

	@PutMapping("/festivalModify")
	public String festivalModify(@RequestPart(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, @RequestPart("festival") FestivalUpdateDto festDto)
			throws IOException, ParseException {

		// DTO로 받아서 ENTITY에 넣어..? YES
		System.out.println("festivalModify.................");
		System.out.println(festDto);
		System.out.println(file);
		String jwt = request.getHeader("Authorization");
		System.out.println(jwt);
		System.out.println(festDto.getStartDate());
		System.out.println(festDto.getEndDate());

		try {
			String fileUrl = null;
			String thumbnailUrl = null;

			Optional<Festival> optionalEntity = festivalRepository.findById(festDto.getUcSeq());
			if (!optionalEntity.isPresent()) {

				throw new IllegalArgumentException("축제 정보를 찾을 수 없습니다....");
			}

			Festival entity = optionalEntity.get();

			if (file != null && !file.isEmpty()) { // 첨부파일이 있을 경우에만 이미지 URL 업데이트

				// 파일 처리 로직 추가
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

				fileUrl = "http://localhost:9002/uploads/" + fileName;

				// 썸네일 생성 및 저장?
				String thumbnailFilename = "thumb_" + fileName;
				Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFilename);
				Files.copy(file.getInputStream(), thumbnailPath, StandardCopyOption.REPLACE_EXISTING);

				thumbnailUrl = "http://localhost:9002/uploads/" + thumbnailFilename;

				// 이미지 URL을 엔티티에 설정
				entity.setMainImgNormal(fileUrl);
				entity.setMainImgThumb(thumbnailUrl);
			}

			Date startDate = null;
			Date endDate = null;

			// 날짜 타입으로 바꾸기
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			if (festDto.getStartDate() != null && !festDto.getStartDate().isEmpty()) {
				String s_startDate = festDto.getStartDate();
				startDate = formatter.parse(s_startDate);
			} else if (festDto.getStartDate() == null && festDto.getStartDate().isEmpty()) {
				startDate = null;
			}

			if (festDto.getEndDate() != null && !festDto.getStartDate().isEmpty()) {
				String s_endDate = festDto.getEndDate();
				endDate = formatter.parse(s_endDate);
			} else if (festDto.getEndDate() == null && festDto.getEndDate().isEmpty()) {
				endDate = null;
			}

			System.out.println(startDate);
			System.out.println(endDate);

			// Date 객체를 문자열로 변환하여 출력 (그냥 해봄)
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formattedStartDate = startDate != null ? outputFormat.format(startDate) : null;
			String formattedEndDate = endDate != null ? outputFormat.format(endDate) : null;

			System.out.println(formattedStartDate);
			System.out.println(formattedEndDate);

			if (jwt != null) {

				entity.setUcSeq(festDto.getUcSeq());
				entity.setMainTitle(festDto.getMaintitle());
				entity.setTitle(festDto.getTitle());
				entity.setSubtitle(festDto.getSubtitle());
				entity.setMainPlace(festDto.getMainPlace());
				entity.setPlace(festDto.getPlace());
				entity.setItemCntnts(festDto.getItemcntnts());
				entity.setAddr1(festDto.getAddr1());
				entity.setAddr2(festDto.getAddr2());
				entity.setGugunNm(festDto.getGugunNm());
				entity.setCntctTel(festDto.getCntctTel());
				entity.setHomepageUrl(festDto.getHomepageUrl());
				entity.setUsageDayWeekAndTime(festDto.getUsageDayWeekAndTime());
				entity.setStartDate(startDate);
				entity.setEndDate(endDate);
				entity.setUsageDay(festDto.getUsageDay());
				entity.setUsageAmount(festDto.getUsageAmount());
				entity.setMiddleSizeRm1(festDto.getMiddleSizeRm1());
				entity.setTrfcInfo(festDto.getTrfcInfo());
				entity.setLat(Double.parseDouble(festDto.getLat()));
				entity.setLng(Double.parseDouble(festDto.getLng()));

				/*
				 * if (fileUrl != null && thumbnailUrl != null) {
				 * 
				 * entity.setMainImgNormal(fileUrl); entity.setMainImgThumb(thumbnailUrl); }
				 */

				System.out.println("db에 저장할 축제 엔티티 = " + entity);
				festivalRepository.save(entity);

			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		return "축제 정보가 성공적으로 수정되었습니다..";
	}

	
	@DeleteMapping("/festivalDelete/{uc_seq}")
	public ResponseEntity<String> deleteFestival(@PathVariable("uc_seq") String uc_seq, HttpServletRequest request){
		
		System.out.println("deleteFestival.........................");
		
		String token = request.getHeader("Authorization");
		System.out.println(token);
		
		Long uc_seq_long = Long.parseLong(uc_seq);
		
		if(token != null) {
			festivalRepository.deleteById(uc_seq_long);
			
			return new ResponseEntity<>("게시물이 성공적으로 삭제되었습니다.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("삭제권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
	}
	
}
