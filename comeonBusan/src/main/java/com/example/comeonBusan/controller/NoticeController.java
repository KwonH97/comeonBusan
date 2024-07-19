package com.example.comeonBusan.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.comeonBusan.entity.Help;
import com.example.comeonBusan.repository.HelpRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/khj")
@RestController
@Slf4j
public class NoticeController {

	@Autowired
	private HelpRepository helpRepository;

	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

	// 파일 저장 장소
	private static final String UPLOAD_DIR = "C:/uploads/";

	// 파일 확장자 추출 메서드
	private String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	// 공지사항

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

	@PostMapping("/noticeAdd")
	public String noticeAdd2(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("title") String title, @RequestParam("content") String content, HttpServletRequest request) {

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

			if (file != null && !file.isEmpty()) {

				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

				fileUrl = "http://localhost:9002/uploads/" + fileName;

				// 썸네일 생성 및 저장?
				String thumbnailFilename = "thumb_" + fileName;
				Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFilename);
				Files.copy(file.getInputStream(), thumbnailPath, StandardCopyOption.REPLACE_EXISTING);

				thumbnailUrl = "http://localhost:9002/uploads/" + thumbnailFilename;

			}

			if (jwt != null) {

				help.setTitle(title);
				help.setContent(content);
				help.setRegDate(LocalDate.now());

				if (fileUrl != null && thumbnailUrl != null) {

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

}
