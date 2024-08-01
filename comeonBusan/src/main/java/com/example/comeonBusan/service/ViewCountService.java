package com.example.comeonBusan.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.comeonBusan.entity.Festival;
import com.example.comeonBusan.entity.Food;
import com.example.comeonBusan.entity.TourList;
import com.example.comeonBusan.entity.Views;
import com.example.comeonBusan.repository.ViewsRepository;

import jakarta.transaction.Transactional;

@Service
public class ViewCountService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ViewsRepository viewsRepository;

    private static final String VIEW_COUNT_KEY = "view:count:";

    public void incrementTourViewCount(String tourlistUcSeq) {
        String viewCountKey = VIEW_COUNT_KEY + "tour:" + tourlistUcSeq;
        redisTemplate.opsForValue().increment(viewCountKey);
    }

    public void incrementFestivalViewCount(Long festivalUcSeq) {
        String viewCountKey = VIEW_COUNT_KEY + "festival:" + festivalUcSeq;
        redisTemplate.opsForValue().increment(viewCountKey);
    }

    public void incrementFoodViewCount(String foodUcSeq) {
        String viewCountKey = VIEW_COUNT_KEY + "food:" + foodUcSeq;
        redisTemplate.opsForValue().increment(viewCountKey);
    }

    public Long getViewCountForTour(String tourlistUcSeq) {
        String viewCountKey = VIEW_COUNT_KEY + "tour:" + tourlistUcSeq;
        return getViewCount(viewCountKey);
    }

    public Long getViewCountForFestival(Long festivalUcSeq) {
        String viewCountKey = VIEW_COUNT_KEY + "festival:" + festivalUcSeq;
        return getViewCount(viewCountKey);
    }

    public Long getViewCountForFood(String foodUcSeq) {
        String viewCountKey = VIEW_COUNT_KEY + "food:" + foodUcSeq;
        return getViewCount(viewCountKey);
    }

    private Long getViewCount(String viewCountKey) {
        Object viewCountObject = redisTemplate.opsForValue().get(viewCountKey);
        if (viewCountObject instanceof Integer) {
            return ((Integer) viewCountObject).longValue();
        } else if (viewCountObject instanceof Long) {
            return (Long) viewCountObject;
        } else {
            return 0L;
        }
    }

    @Scheduled(fixedRate = 600000) // 10분마다 실행
    @Transactional
    public void updateViewCountsInDb() {
        Set<String> keys = redisTemplate.keys(VIEW_COUNT_KEY + "*");
        if (keys != null) {
            for (String key : keys) {
                String[] parts = key.replace(VIEW_COUNT_KEY, "").split(":");
                if (parts.length == 2) {
                    String type = parts[0];
                    String ucSeq = parts[1];
                    Long redisViewCount = getViewCount(key);

                    if (redisViewCount != null) {
                        Views view = null;
                        if ("tour".equals(type)) {
                            view = viewsRepository.findByTourlistUcSeq(ucSeq);
                            if (view == null) {
                                view = new Views();
                                TourList tour = new TourList();
                                tour.setUc_seq(ucSeq);
                                view.setTourlist(tour);
                                view.setFestival(null);
                                view.setFood(null);
                                view.setViewcount(redisViewCount);
                            } else {
                                view.setViewcount(redisViewCount); // 덮어씌우기
                            }
                        } else if ("festival".equals(type)) {
                            view = viewsRepository.findByFestivalUcSeq(Long.valueOf(ucSeq));
                            if (view == null) {
                                view = new Views();
                                Festival festival = new Festival();
                                festival.setUcSeq(Long.valueOf(ucSeq));
                                view.setFestival(festival);
                                view.setTourlist(null);
                                view.setFood(null);
                                view.setViewcount(redisViewCount);
                            } else {
                                view.setViewcount(redisViewCount); // 덮어씌우기
                            }
                        } else if ("food".equals(type)) {
                            view = viewsRepository.findByFoodUcSeq(ucSeq);
                            if (view == null) {
                                view = new Views();
                                Food food = new Food();
                                food.setUC_SEQ(ucSeq);
                                view.setFood(food);
                                view.setTourlist(null);
                                view.setFestival(null);
                                view.setViewcount(redisViewCount);
                            } else {
                                view.setViewcount(redisViewCount); // 덮어씌우기
                            }
                        }
                        viewsRepository.save(view);
                    }
                }
            }
        }
    }
}
