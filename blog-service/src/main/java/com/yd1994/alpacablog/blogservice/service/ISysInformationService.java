package com.yd1994.alpacablog.blogservice.service;

import com.yd1994.alpacablog.blogservice.dto.SysInformation;

/**
 * <p>
 * SysInformation Service 接口
 * </p>
 *
 * @author yd
 */
public interface ISysInformationService extends IBaseService<SysInformation> {

    SysInformation get(String name) throws Exception;

    int updateByName(SysInformation sysInformation, String username) throws Exception;

}
