package com.yd1994.alpacablog.blogservice.controller;


import com.yd1994.alpacablog.blogservice.common.http.RestRequestParam;
import com.yd1994.alpacablog.blogservice.dto.Article;
import com.yd1994.alpacablog.blogservice.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

/**
 * <p>
 * 博文表 前端控制器
 * </p>
 *
 * @author yd
 */
@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @GetMapping
    public ResponseEntity list(RestRequestParam restRequestParam, Long categoryId) {
        return ResponseEntity.ok(this.articleService.list(restRequestParam, categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) throws Exception {
        Article article = this.articleService.get(id);
        article.setTraffic(this.articleService.incrementTraffic(article.getId()));
        return ResponseEntity.ok(article);
    }

    @GetMapping("/{id}/content")
    public ResponseEntity getContent(@PathVariable("id") Long id) throws Exception {
        Article article = this.articleService.get(id);
        return ResponseEntity.ok(article.getContent());
    }

    @PreAuthorize("#oauth2.hasAnyScope('server', 'client') and hasAuthority('admin')")
    @PostMapping
    public ResponseEntity add(Article article, Principal principal) throws Exception {
        this.articleService.add(article, principal.getName());

        URI location = null;
        try {
            location = new URI("/articles/" + article.getId());
        } catch (URISyntaxException e) {
            log.info(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统出错，请联系管理员。");
        }
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("#oauth2.hasAnyScope('server', 'client') and hasAuthority('admin')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, Article article, Principal principal) throws Exception {
        article.setId(id);

        this.articleService.update(article, principal.getName());

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("#oauth2.hasAnyScope('server', 'client') and hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id, Principal principal) throws Exception {
        this.articleService.delete(id, principal.getName());
        return ResponseEntity.ok().build();
    }

}
