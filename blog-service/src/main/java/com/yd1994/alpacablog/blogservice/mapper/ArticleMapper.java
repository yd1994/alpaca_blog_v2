package com.yd1994.alpacablog.blogservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yd1994.alpacablog.blogservice.common.http.RestRequestParam;
import com.yd1994.alpacablog.blogservice.entity.ArticleDO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 博文表 Mapper 接口
 * </p>
 *
 * @author yd
 */
@Repository
public interface ArticleMapper extends BaseMapper<ArticleDO> {

    ArticleDO getById(Long id);

    List<ArticleDO> list(@Param("param") RestRequestParam restRequestParam,
                         @Param("limitStart") int limitStart, @Param("limitSize") int limitSize, @Param("categoryId") Long categoryId);

    Long listTotal(@Param("param") RestRequestParam restRequestParam, @Param("categoryId") Long categoryId);

}
