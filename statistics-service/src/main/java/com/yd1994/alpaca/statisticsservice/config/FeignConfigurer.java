package com.yd1994.alpaca.statisticsservice.config;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfigurer {

    @Bean
    public JiebaSegmenter jiebaSegmenter() {
        return new JiebaSegmenter();
    }

}
