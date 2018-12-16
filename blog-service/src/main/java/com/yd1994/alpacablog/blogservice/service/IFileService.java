package com.yd1994.alpacablog.blogservice.service;

import com.yd1994.alpacablog.blogservice.dto.FileInfo;

/**
 * <p>
 * 文件信息 服务类
 * </p>
 *
 * @author yd
 */
public interface IFileService extends IBaseService<FileInfo> {

    /**
     * 通过id与type获取
     * @param id
     * @param type 类型
     * @return
     */
    FileInfo get(Long id, Integer type) throws Exception;

}
