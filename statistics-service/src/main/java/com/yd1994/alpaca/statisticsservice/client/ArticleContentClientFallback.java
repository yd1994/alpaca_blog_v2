package com.yd1994.alpaca.statisticsservice.client;

/**
 * @author yd
 */
public class ArticleContentClientFallback implements ArticleContentClient {

    private static final String DEFAULT_CONTENT = "";

    @Override
    public String getContent(Long id) {
        return DEFAULT_CONTENT;
    }
}
