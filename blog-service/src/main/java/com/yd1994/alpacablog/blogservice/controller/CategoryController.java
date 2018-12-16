package com.yd1994.alpacablog.blogservice.controller;


import com.yd1994.alpacablog.blogservice.common.http.RestRequestParam;
import com.yd1994.alpacablog.blogservice.dto.Category;
import com.yd1994.alpacablog.blogservice.service.IArticleService;
import com.yd1994.alpacablog.blogservice.service.ICategoryService;
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
 * 分类 前端控制器
 * </p>
 *
 * @author yd
 */
@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IArticleService articleService;

    @GetMapping("/{categoryId}/articles")
    public ResponseEntity listArticles(RestRequestParam restRequestParam, @PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(this.articleService.list(restRequestParam, categoryId));
    }

    @GetMapping
    public ResponseEntity list(RestRequestParam restRequestParam) throws Exception {
        return ResponseEntity.ok(this.categoryService.list(restRequestParam));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(this.categoryService.get(id));
    }

    @PreAuthorize("#oauth2.hasAnyScope('server', 'client') and hasAuthority('admin')")
    @PostMapping
    public ResponseEntity add(Category category, Principal principal) throws Exception {
        this.categoryService.add(category, principal.getName());

        URI location = null;
        try {
            location = new URI("/categories/" + category.getId());
        } catch (URISyntaxException e) {
            log.info(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统出错，请联系管理员。");
        }
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("#oauth2.hasAnyScope('server', 'client') and hasAuthority('admin')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, Category category, Principal principal) throws Exception {
        category.setId(id);

        this.categoryService.update(category, principal.getName());

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("#oauth2.hasAnyScope('server', 'client') and hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id, Principal principal) throws Exception {
        this.categoryService.delete(id, principal.getName());
        return ResponseEntity.ok().build();
    }

}
