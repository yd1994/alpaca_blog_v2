package com.yd1994.alpacablog.blogservice;

import com.yd1994.alpacablog.blogservice.dto.Article;
import com.yd1994.alpacablog.blogservice.dto.ArticleTag;
import com.yd1994.alpacablog.blogservice.dto.Category;
import com.yd1994.alpacablog.blogservice.service.IArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTests {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IArticleService articleService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void add() throws Exception {
        Article article = new Article();
        article.setTitle("test0003");
        article.setSummary("testsetSummary0003");
        article.setContent("testsetContent0003");
        Category category = new Category();
        category.setId(6L);
        article.setCategory(new Category());

        List<ArticleTag> articleTagSet = new ArrayList<>();
        ArticleTag articleTag1 = new ArticleTag();
        articleTag1.setId(1L);
        ArticleTag articleTag2 = new ArticleTag();
        articleTag2.setId(2L);
        articleTagSet.add(articleTag1);
        articleTagSet.add(articleTag2);

        article.setArticleTagSet(articleTagSet);

        this.articleService.add(article, "admin");
        log.info("articleId: " + article.getId());
    }

}
