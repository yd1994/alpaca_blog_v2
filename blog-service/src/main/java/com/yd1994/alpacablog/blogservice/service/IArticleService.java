package com.yd1994.alpacablog.blogservice.service;

import com.yd1994.alpacablog.blogservice.common.http.ResponseBody;
import com.yd1994.alpacablog.blogservice.common.http.RestRequestParam;
import com.yd1994.alpacablog.blogservice.dto.Article;

import java.util.List;

/**
 * <p>
 * 博文表 服务类
 * </p>
 *
 * @author yd
 */
public interface IArticleService extends IBaseService<Article> {

    ResponseBody<Article> list(RestRequestParam restRequestParam, Long categoryId);

    /**
     * 将 Article traffic + 1 并获取
     * @param id Article id
     * @return
     */
    Long incrementTraffic(Long id);

}
