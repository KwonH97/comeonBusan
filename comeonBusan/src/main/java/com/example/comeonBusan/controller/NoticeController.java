package com.example.comeonBusan.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.comeonBusan.entity.Help;
import com.example.comeonBusan.repository.HelpRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@RequestMapping("/khj")
@RestController
@Slf4j
public class NoticeController {

    private final List<SseEmitter> clients = new CopyOnWriteArrayList<>();

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

    // 공지사항 리스트 조회
    @GetMapping("/noticeList")
    public List<Help> getNoticeList() {
        System.out.println("getNoticeList..............");
        return helpRepository.findAll();
    }

    // 공지사항 상세 조회
    @GetMapping("/noticeDetail/{hnum}")
    public Help getNoticeDetail(@PathVariable("hnum") String hnum) {
        System.out.println("getNotieDetail............(rest Controller)");
        Long hnum_long = Long.parseLong(hnum);
        System.out.println("long타입으로 바꾼 hnum : " + hnum_long);
        return helpRepository.findById(hnum_long)
                             .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID: " + hnum));
    }

    // 공지사항 삭제
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

    // SSE 이벤트 스트림 생성
    @GetMapping("/events")
    public SseEmitter streamSseMvc() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // 타임아웃 제거
        clients.add(emitter);
        emitter.onCompletion(() -> clients.remove(emitter));
        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onError((e) -> clients.remove(emitter));
        return emitter;
    }

    // 공지사항 등록
    @PostMapping("/noticeAdd")
    public String noticeAdd2(@RequestParam(value = "file", required = false) MultipartFile file,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             HttpServletRequest request) {

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

                // 썸네일 생성 및 저장
                String thumbnailFilename = "thumb_" + fileName;
                Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFilename);
                Thumbnails.of(file.getInputStream()).size(200, 200).toFile(thumbnailPath.toFile());

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

                // 저장된 공지사항을 실시간으로 전송
                List<SseEmitter> deadEmitters = new CopyOnWriteArrayList<>();
                for (SseEmitter emitter : clients) {
                    try {
                        emitter.send(SseEmitter.event()
                                .name("notice")
                                .data(help, MediaType.APPLICATION_JSON));
                        System.out.println("공지사항 전송됨: " + help);
                    } catch (IOException e) {
                        deadEmitters.add(emitter);
                       
                        System.err.println("SSE 클라이언트 제거: " + e.getMessage());
                    } 
                }
                System.out.println("final 다음..............");
                // 끊어진 연결 제거
                clients.removeAll(deadEmitters);
            }

        } catch (IOException e) {
        	 System.out.println("바깥쪽 try 예외처리..................");
            e.printStackTrace();
        }

        return "공지가 성공적으로 등록되었습니다.";
    }

    // 공지사항 수정
    @PutMapping("/noticeModify/{hnum}")
    public String noticeModify(@PathVariable("hnum") String hnum,
                               @RequestParam(value = "file", required = false) MultipartFile file,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               HttpServletRequest request) {

        System.out.println("noticeModify...........");
        System.out.println(title);
        System.out.println(content);
        System.out.println(file);
        String jwt = request.getHeader("Authorization");
        System.out.println(jwt);
        Long hnum_long = Long.parseLong(hnum);

        try {
            String fileUrl = null;
            String thumbnailUrl = null;

            Optional<Help> optionalEntity = helpRepository.findById(hnum_long);

            if (!optionalEntity.isPresent()) {
                throw new IllegalArgumentException("공지사항 정보를 찾을 수 없습니다......");
            }

            Help entity = optionalEntity.get();

            if (file != null && !file.isEmpty()) { // 첨부파일이 있을 경우에만 이미지 URL 업데이트
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

                // 이미지 URL을 엔티티에 설정
                entity.setHimg(fileUrl);
                entity.setHThumbnailImg(thumbnailUrl);
            }

            if (jwt != null) {
                entity.setHnum(hnum_long);
                entity.setTitle(title);
                entity.setContent(content);
                entity.setRegDate(LocalDate.now());

                System.out.println("DB에 저장할 공지사항 엔티티" + entity);
                helpRepository.save(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "success";
    }
}
