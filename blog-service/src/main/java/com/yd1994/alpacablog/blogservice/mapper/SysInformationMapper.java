package com.yd1994.alpacablog.blogservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yd1994.alpacablog.blogservice.entity.SysInformationDO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统信息表 Mapper 接口
 * </p>
 *
 * @author yd
 */
@Repository
public interface SysInformationMapper extends BaseMapper<SysInformationDO> {

}
