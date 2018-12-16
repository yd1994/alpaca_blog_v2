package com.yd1994.alpacablog.blogservice.service;

import com.yd1994.alpacablog.blogservice.common.http.ResponseBody;
import com.yd1994.alpacablog.blogservice.common.http.RestRequestParam;
import com.yd1994.alpacablog.blogservice.dto.Category;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author yd
 * @since 2018-11-26
 */
public interface ICategoryService extends IBaseService<Category> {

    ResponseBody<Category> list(RestRequestParam restRequestParam) throws Exception;

}
