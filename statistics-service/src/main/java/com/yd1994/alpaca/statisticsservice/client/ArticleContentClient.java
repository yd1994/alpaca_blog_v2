package com.yd1994.alpaca.statisticsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yd
 */
@FeignClient(name = "blog-service", fallback = ArticleContentClientFallback.class)
public interface ArticleContentClient {

    @RequestMapping(value = "/articles/{id}/content", method = RequestMethod.GET)
    String getContent(@PathVariable("id") Long id);

}
