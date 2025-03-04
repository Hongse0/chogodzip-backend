package com.kb.openApiRefactoring.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenApiService {

    private static final String API_KEY = "71434f686774617933387952755651";
    private static final String API_URL = "http://openapi.seoul.go.kr:8088/{API_KEY}/json/tbLnOpendataRentV/{START_INDEX}/{END_INDEX}/2024/%20/{gu}";

    private final RestTemplate restTemplate;

    public OpenApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "realEstateData", key = "#gu")
    public String getRealEstateData(String gu) {
        String url = API_URL.replace("{API_KEY}", API_KEY)
                .replace("{START_INDEX}", "1")
                .replace("{END_INDEX}", "1000")
                .replace("{gu}", gu);

        return restTemplate.getForObject(url, String.class);
    }

    // 1시간마다 캐시 삭제 (데이터 갱신 주기 설정)
    @CacheEvict(value = "realEstateData", allEntries = true)
    @Scheduled(fixedRate = 3600000) // 1시간(3600000ms)마다 실행
    public void evictRealEstateCache() {
        System.out.println("캐시 데이터 삭제: realEstateData");
    }
}
