package com.bilibili.dao;

import com.bilibili.domain.User;

/**
 * @Classname
 * @Description
 * @Date 2020/4/22 19:47
 * @Create by gt
 */
public interface UserDao {
    User findUserByPhone(String phone);

    void save(User user);
}
