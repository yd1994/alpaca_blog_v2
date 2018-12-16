package com.yd1994.alpacablog.blogservice.service;

import com.yd1994.alpacablog.blogservice.common.http.ResponseBody;
import com.yd1994.alpacablog.blogservice.common.http.RestRequestParam;
import com.yd1994.alpacablog.blogservice.dto.ArticleTag;
import com.yd1994.alpacablog.blogservice.entity.ArticleTagDO;

import java.util.List;

/**
 * <p>
 * 博文表 服务类
 * </p>
 *
 * @author yd
 */
public interface IArticleTagService extends IBaseService<ArticleTag> {

    ResponseBody<ArticleTag> list(RestRequestParam restRequestParam);

}
