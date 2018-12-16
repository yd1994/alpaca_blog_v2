package com.yd1994.alpacablog.blogservice;

import com.yd1994.alpacablog.blogservice.entity.ArticleDO;
import com.yd1994.alpacablog.blogservice.service.IArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogServiceApplicationTests {

    private Logger log = LoggerFactory.getLogger(getClass());

    private IArticleService articleService;

    @Test
    public void contextLoads() {
    }

}
