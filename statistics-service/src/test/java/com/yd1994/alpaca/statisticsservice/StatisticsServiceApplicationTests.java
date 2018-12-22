package com.yd1994.alpaca.statisticsservice;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.yd1994.alpaca.statisticsservice.service.ArticleContentWordCloudService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceApplicationTests {

    @Autowired
    private ArticleContentWordCloudService articleContentWordCloudService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testArticleContentWordCloudService() {
        Map<String, Integer> result = this.articleContentWordCloudService.get(1L);
        result.forEach((word, content) -> {
            log.info(word + ": " + content);
        });
    }
}

