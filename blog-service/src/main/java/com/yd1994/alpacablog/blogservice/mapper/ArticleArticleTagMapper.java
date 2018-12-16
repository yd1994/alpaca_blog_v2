package com.yd1994.alpacablog.blogservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yd1994.alpacablog.blogservice.entity.ArticleArticleTagDO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 博文-标签 关系表 Mapper 接口
 * </p>
 *
 * @author yd
 */
@Repository
public interface ArticleArticleTagMapper extends BaseMapper<ArticleArticleTagDO> {

}
