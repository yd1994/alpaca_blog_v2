package com.yd1994.alpacablog.authservice.service;


import com.yd1994.alpacablog.authservice.entity.UserDO;

/**
 * 用户 Service
 *
 * @author yd
 */
public interface UserService {

    /**
     * 通过 id 获取
     * @param id
     * @return
     */
    UserDO getById(Long id);

    /**
     * 通过 username 获取
     * @param username
     * @return
     */
    UserDO getByUsername(String username);

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updateUserPassword(String oldPassword, String newPassword, String username);

}
