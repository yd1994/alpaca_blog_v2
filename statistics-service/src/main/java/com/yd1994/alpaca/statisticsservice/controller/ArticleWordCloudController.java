package com.yd1994.alpaca.statisticsservice.controller;

import com.yd1994.alpaca.statisticsservice.service.ArticleContentWordCloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleWordCloudController {

    @Autowired
    private ArticleContentWordCloudService articleContentWordCloudService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/content/word_cloud")
    public ResponseEntity getWordCloud(@PathVariable("id") Long id) {
        return ResponseEntity.ok(articleContentWordCloudService.get(id));
    }

}
