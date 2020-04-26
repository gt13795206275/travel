package com.bilibili.service;

import com.bilibili.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname
 * @Description
 * @Date 2020/4/22 19:44
 * @Create by gt
 */
public interface UserService {
    String validatePhone(String phone);

    String register(User user);

    String login(String phone, String password, HttpServletRequest request);

}
