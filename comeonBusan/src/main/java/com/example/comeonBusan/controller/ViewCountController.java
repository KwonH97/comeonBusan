//package com.example.comeonBusan.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.comeonBusan.service.ViewCountService;
//
//@RestController
//@RequestMapping("/views")
//public class ViewCountController {
//
//    @Autowired
//    private ViewCountService viewCountService;
//
//    // 조회수 증가 엔드포인트
//    @PostMapping("/tour/{ucSeq}")
//    public void incrementTourViewCount(@PathVariable("ucSeq") String ucSeq) {
//        viewCountService.incrementTourViewCount(ucSeq);
//    }
//
//    @PostMapping("/festival/{ucSeq}")
//    public void incrementFestivalViewCount(@PathVariable("ucSeq") Long ucSeq) {
//        viewCountService.incrementFestivalViewCount(ucSeq);
//    }
//
//    @PostMapping("/food/{ucSeq}")
//    public void incrementFoodViewCount(@PathVariable("ucSeq") String ucSeq) {
//        viewCountService.incrementFoodViewCount(ucSeq);
//    }
//
//    // 조회수 가져오기 엔드포인트
//    @GetMapping("/tour/{ucSeq}")
//    public Long getTourViewCount(@PathVariable("ucSeq") String ucSeq) {
//        return viewCountService.getViewCountForTour(ucSeq);
//    }
//
//    @GetMapping("/festival/{ucSeq}")
//    public Long getFestivalViewCount(@PathVariable("ucSeq") Long ucSeq) {
//        return viewCountService.getViewCountForFestival(ucSeq);
//    }
//
//    @GetMapping("/food/{ucSeq}")
//    public Long getFoodViewCount(@PathVariable("ucSeq") String ucSeq) {
//        return viewCountService.getViewCountForFood(ucSeq);
//    }
//}
//
