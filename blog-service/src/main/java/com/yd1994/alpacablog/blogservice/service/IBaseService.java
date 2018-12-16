package com.yd1994.alpacablog.blogservice.service;

/**
 * <p>服务类 基类</p>
 *
 * @author yd
 */
public interface IBaseService<D> {

    D get(Long id) throws Exception;

    int add(D dto, String username) throws Exception;

    int update(D dto, String username) throws Exception;

    int delete(Long id, String username) throws Exception;

}
