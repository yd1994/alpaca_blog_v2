package com.yd1994.alpaca.statisticsservice.service.impl;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.yd1994.alpaca.statisticsservice.client.ArticleContentClient;
import com.yd1994.alpaca.statisticsservice.service.ArticleContentWordCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleContentWordCloudServiceImpl implements ArticleContentWordCloudService {

    @Autowired
    private JiebaSegmenter jiebaSegmenter;

    @Autowired
    private ArticleContentClient articleContentClient;

    @Override
    public Map<String, Integer> get(Long id) {
        String content = this.articleContentClient.getContent(id);

        List<SegToken> segTokenList = jiebaSegmenter.process(content, JiebaSegmenter.SegMode.INDEX);

        Map<String, Integer> result = new HashMap<>();

        segTokenList.stream().forEach(segToken -> {
            Integer i = result.get(segToken.word);
            result.put(segToken.word, i == null ? 1 : i + 1);
        });

        return result;
    }

}
