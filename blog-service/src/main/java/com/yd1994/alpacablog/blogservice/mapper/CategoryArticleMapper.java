package com.yd1994.alpacablog.blogservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yd1994.alpacablog.blogservice.entity.CategoryArticleDO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 博文-分类 关系表 Mapper 接口
 * </p>
 *
 * @author yd
 */
@Repository
public interface CategoryArticleMapper extends BaseMapper<CategoryArticleDO> {

}
