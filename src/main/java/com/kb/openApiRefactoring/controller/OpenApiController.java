package com.kb.openApiRefactoring.controller;

import com.kb.openApiRefactoring.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiService openApiService;
    
    @GetMapping("/api/real-estate/{gu}")
    public String getRealEstateData(@PathVariable String gu) {
        return openApiService.getRealEstateData(gu);
    }
}
