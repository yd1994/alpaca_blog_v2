package com.yd1994.alpacablog.blogservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yd1994.alpacablog.blogservice.entity.ArticleTagDO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 标签 Mapper 接口
 * </p>
 *
 * @author yd
 */
@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTagDO> {

}
