package com.example.comeonBusan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.comeonBusan.dto.SearchResult;
import com.example.comeonBusan.entity.Festival;
import com.example.comeonBusan.entity.Food;
import com.example.comeonBusan.entity.Lodgment;
import com.example.comeonBusan.entity.TourList;
import com.example.comeonBusan.repository.FestivalRepository;
import com.example.comeonBusan.repository.FoodRepository;
import com.example.comeonBusan.repository.LodgmentRepository;
import com.example.comeonBusan.repository.TourlistRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class SearchService {

    @Autowired
    private LodgmentRepository lodgmentRepository;
    
    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private TourlistRepository tourListRepository;

//     통합 검색 메서드
    public List<SearchResult> search(String query) {
        List<SearchResult> results = new ArrayList<>();
        results.addAll(convertLodgmentsToSearchResult(searchLodgments(query)));
        results.addAll(convertFestivalsToSearchResult(searchFestivals(query)));
        results.addAll(convertFoodsToSearchResult(searchFoods(query)));
        results.addAll(convertTourListsToSearchResult(searchTourLists(query)));
        return results;
    }

//     개별 엔티티 검색 메서드
    private List<Lodgment> searchLodgments(String query) {
        Specification<Lodgment> specification = getLodgmentSpecification(query);
        return lodgmentRepository.findAll(specification);
    }

    private List<Festival> searchFestivals(String query) {
        Specification<Festival> specification = getFestivalSpecification(query);
        return festivalRepository.findAll(specification);
    }

    private List<Food> searchFoods(String query) {
        Specification<Food> specification = getFoodSpecification(query);
        return foodRepository.findAll(specification);
    }

    private List<TourList> searchTourLists(String query) {
        Specification<TourList> specification = getTourListSpecification(query);
        return tourListRepository.findAll(specification);
    }

//     엔티티를 SearchResult DTO로 변환하는 메서드들
    private List<SearchResult> convertLodgmentsToSearchResult(List<Lodgment> lodgments) {
        List<SearchResult> results = new ArrayList<>();
        for (Lodgment lodgment : lodgments) {
            SearchResult result = new SearchResult();
            result.setType("Lodgment");
            result.setName(lodgment.get업체명());
            results.add(result);
        }
        return results;
    }

    private List<SearchResult> convertFestivalsToSearchResult(List<Festival> festivals) {
        List<SearchResult> results = new ArrayList<>();
        for (Festival festival : festivals) {
            SearchResult result = new SearchResult();
            result.setType("Festival");
            result.setName(festival.getTitle());
            result.setDescription(festival.getMainImgThumb()); // 필요에 따라 실제 설명 필드로 변경
            results.add(result);
        }
        return results;
    }

    private List<SearchResult> convertFoodsToSearchResult(List<Food> foods) {
        List<SearchResult> results = new ArrayList<>();
        for (Food food : foods) {
            SearchResult result = new SearchResult();
            result.setType("Food");
            result.setName(food.getMain_title());
            result.setDescription(food.getMain_img_thumb()); // 필요에 따라 실제 설명 필드로 변경
            results.add(result);
        }
        return results;
    }

    private List<SearchResult> convertTourListsToSearchResult(List<TourList> tourLists) {
        List<SearchResult> results = new ArrayList<>();
        for (TourList tourList : tourLists) {
            SearchResult result = new SearchResult();
            result.setType("TourList");
            result.setName(tourList.getMaintitle());
            result.setDescription(tourList.getMain_img_thumb());
            results.add(result);
        }
        return results;
    }

//     Specification 메서드들
    private Specification<Lodgment> getLodgmentSpecification(String query) {
        return (root, queryObj, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query != null && !query.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("업체명"), "%" + query + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Specification<Festival> getFestivalSpecification(String query) {
        return (root, queryObj, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query != null && !query.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("mainTitle"), "%" + query + "%"));
                predicates.add(criteriaBuilder.like(root.get("subtitle"), "%" + query + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Specification<Food> getFoodSpecification(String query) {
        return (root, queryObj, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query != null && !query.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("main_title"), "%" + query + "%"));
                predicates.add(criteriaBuilder.like(root.get("itemcntnts"), "%" + query + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Specification<TourList> getTourListSpecification(String query) {
        return (root, queryObj, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query != null && !query.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("maintitle"), "%" + query + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

