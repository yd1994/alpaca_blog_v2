package com.yd1994.alpaca.statisticsservice.service;

import java.util.Map;

/**
 * @author yd
 */
public interface ArticleContentWordCloudService {

    Map<String, Integer> get(Long id);

}
