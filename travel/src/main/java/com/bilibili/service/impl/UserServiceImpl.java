package com.bilibili.service.impl;

import com.alibaba.fastjson.JSON;
import com.bilibili.dao.UserDao;
import com.bilibili.dao.impl.UserDaoImpl;
import com.bilibili.domain.User;
import com.bilibili.service.UserService;
import com.bilibili.utils.BeansFactory2;
import com.bilibili.utils.Md5Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname
 * @Description
 * @Date 2020/4/22 19:45
 * @Create by gt
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = BeansFactory2.getBeans("userDao");

    @Override
    public String validatePhone(String phone) {
        User user = userDao.findUserByPhone(phone);
        Map<String, String> map = new HashMap<String, String>();
        if (user == null) {//手机号可以注册
            map.put("message", "no");
        } else {
            map.put("message", "yes");
        }
        return JSON.toJSONString(map);
    }

    @Override
    public String register(User user) {
        //对密码进行加密
        try {
            user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDao.save(user);
        Map<String, String> map = new HashMap<String, String>();
        map.put("message", "success");
        return JSON.toJSONString(map);
    }

    @Override
    public String login(String phone, String password, HttpServletRequest request) {
        //判断手机号是否注册过
        Map<String, String> map = new HashMap<String, String>();
        User user = userDao.findUserByPhone(phone);
        if (user == null) {//手机号没有注册,不允许登录
            map.put("message", "phoneError");
        } else {//手机号注册过,可以登录
            try {
                //判断密码是否正确
                password = Md5Util.encodeByMd5(password);
                if (password.equals(user.getPassword())) {//密码正确
                    map.put("message", "success");
                    request.getSession().setAttribute("user", user);
                } else {//密码错误
                    map.put("message", "passwordError");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return JSON.toJSONString(map);
    }
}
