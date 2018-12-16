package com.yd1994.alpacablog.blogservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yd1994.alpacablog.blogservice.entity.FileDO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文件信息表 Mapper 接口
 * </p>
 *
 * @author yd
 */
@Repository
public interface FileMapper extends BaseMapper<FileDO> {

}
