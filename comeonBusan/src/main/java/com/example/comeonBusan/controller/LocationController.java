package com.example.comeonBusan.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.comeonBusan.dto.DangerZoneDTO;
import com.example.comeonBusan.dto.LocationUpdate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final List<DangerZoneDTO> dangerZones;

    public LocationController() {
        // 위험 지역 초기화
        dangerZones = new ArrayList<>();
        dangerZones.add(new DangerZoneDTO("위험 지역 1", 35.1796, 129.0656, 100));
        dangerZones.add(new DangerZoneDTO("위험 지역 2", 35.1696, 129.0756, 100));
        dangerZones.add(new DangerZoneDTO("위험 지역 3", 35.1896, 129.0856, 100));
    }

    @PostMapping("/location/update")
    public void updateLocation(@RequestBody LocationUpdate update, HttpSession session) {
        String sessionId = session.getId();
        for (DangerZoneDTO zone : dangerZones) {
            if (isNearDangerZone(update.getLat(), update.getLon(), zone)) {
                sendAlert(sessionId, "위험 지역 '" + zone.getName() + "'에 접근했습니다!");
                break;
            }
        }
    }

    @GetMapping("/sse/subscribe")
    public SseEmitter subscribe(HttpSession session) {
        String sessionId = session.getId();
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(sessionId, emitter);
        emitter.onCompletion(() -> emitters.remove(sessionId));
        emitter.onTimeout(() -> emitters.remove(sessionId));
        return emitter;
    }

    private boolean isNearDangerZone(double lat, double lon, DangerZoneDTO zone) {
        double distance = calculateDistance(lat, lon, zone.getLat(), zone.getLon());
        return distance <= zone.getRadius();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // 지구의 반경 (km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c * 1000; // 미터 단위로 변환
    }

    private void sendAlert(String sessionId, String message) {
        SseEmitter emitter = emitters.get(sessionId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                emitters.remove(sessionId);
            }
        }
    }
}