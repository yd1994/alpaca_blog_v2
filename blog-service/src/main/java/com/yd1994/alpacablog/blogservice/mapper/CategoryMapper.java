package com.yd1994.alpacablog.blogservice.mapper;

import com.yd1994.alpacablog.blogservice.entity.CategoryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author yd
 * @since 2018-11-26
 */
@Repository
public interface CategoryMapper extends BaseMapper<CategoryDO> {

}
