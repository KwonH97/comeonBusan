package com.example.comeonBusan.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.comeonBusan.entity.Photo;
import com.example.comeonBusan.repository.PhotoRepository;

import jakarta.servlet.http.HttpServletRequest;
import net.coobird.thumbnailator.Thumbnails;

@RequestMapping("/kibTest")
@RestController
public class PhotoController {
	
	@Autowired
	PhotoRepository photoRepo;
	
	//파일 저장 장소
	private static final String UPLOAD_DIR = "C:/uploads/";
	
	//파일 확장자 추출 메서드
	private String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	private static final String UPLOADED_FOLDER = "src/main/resources/static/images/";

	@PostMapping("/photo")
	public void photoUpload(@RequestParam(value="file", required= false)MultipartFile file,
			@RequestParam("title")String title,
			@RequestParam("shoot_year")String shoot_year,
			@RequestParam("shooter")String shooter,
			@RequestParam("have_agency")String have_agency,
			@RequestParam("hashtag")String hashtag,
			HttpServletRequest request) {
		
		String jwt = request.getHeader("Authorization");
		
		Photo photo = new Photo();
		
		try {
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
			
			if(jwt == null) {
				photo.setTitle(title);
				photo.setShooter(shooter);
				photo.setShoot_year(shoot_year);
				photo.setHave_agency(have_agency);
				photo.setHashtag(hashtag);
				
				if(fileUrl != null && thumbnailUrl != null) {
					photo.setMain_img_normal(fileUrl);
					photo.setMain_img_thumb(thumbnailUrl);
				}
				
				photoRepo.save(photo);
				
				//ResponseEntity<String> 사용해보기
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@PostMapping("/mPhoto")
	public String modifyPhoto(@RequestParam(value="file", required= false)MultipartFile file,
			@RequestParam("pno")String pno,
			@RequestParam("title")String title,
			@RequestParam("shoot_year")String shoot_year,
			@RequestParam("shooter")String shooter,
			@RequestParam("have_agency")String have_agency,
			@RequestParam("hashtag")String hashtag,
			HttpServletRequest request) {
		
		String jwt = request.getHeader("Authorization");
		
		Long photoId = Long.valueOf(pno);
		
		Optional<Photo> result = photoRepo.findById(photoId);
		result.get();
		
		if(result.isPresent()) {
			Photo photo = result.get();
			
			try {
				String fileUrl = null;
				String thumbnailUrl = null;
				
				if(file != null && !file.isEmpty()) {
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path targetLocation = Paths.get(UPLOAD_DIR + fileName);
					Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
					
					fileUrl = "http://localhost:9002/uploads/" + fileName;
					
					String thumbnailFileName = "thumb_" + fileName;
					Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFileName);
					Files.copy(file.getInputStream(), thumbnailPath, StandardCopyOption.REPLACE_EXISTING);
					
					thumbnailUrl = "http://localhost:9002/uploads/" + thumbnailFileName;
					
					photo.setMain_img_normal(fileUrl);
					photo.setMain_img_thumb(thumbnailFileName);
				}
				
				photo.setPno(photoId);
				photo.setTitle(title);
				photo.setShooter(shooter);
				photo.setShoot_year(shoot_year);
				photo.setHave_agency(have_agency);
				photo.setHashtag(hashtag);
				
				photoRepo.save(photo);
				
				return "저장성공";
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return "저장 실패";
	}
	
	@GetMapping("/photo")
	public List<Photo> photoList() {
		List<Photo> list= photoRepo.findAll();
		
		return list;
	}	

}
