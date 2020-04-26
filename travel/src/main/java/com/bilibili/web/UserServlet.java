package com.bilibili.web;

import com.alibaba.fastjson.JSON;
import com.bilibili.domain.User;
import com.bilibili.service.UserService;
import com.bilibili.service.impl.UserServiceImpl;
import com.bilibili.utils.BeansFactory;
import com.bilibili.utils.BeansFactory2;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Classname
 * @Description
 * @Date 2020/4/22 18:07
 * @Create by gt
 */
@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {
    //    private UserService userService = new UserServiceImpl();
    private UserService userService = BeansFactory2.getBeans("userService");

    /**
     * 登录
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        String phone = request.getParameter("username");
        String password = request.getParameter("password");
        //业务处理
        String json = userService.login(phone, password, request);
        //给出响应
        response.getWriter().write(json);
    }

    /**
     * 登录成功显示用户信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void findCurrentUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //业务处理
        User user = (User) request.getSession().getAttribute("user");
        Map<String, String> map = new HashMap<String, String>();
        if (user == null) {
            //没有登录
            map.put("message", "noLogin");
        } else {
            //已登录
            map.put("message", user.getName());
        }
        //响应
        response.getWriter().write(JSON.toJSONString(map));
    }

    /**
     * 注册
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //首先比较用户输入的验证码和服务器端保存的验证码是否一致
        String serverCode = (String) request.getSession().getAttribute("code");
        String userCode = request.getParameter("check");
        String json = "";
        if (StringUtils.isNotEmpty(serverCode) && serverCode.equals(userCode)) {
            //获取所有用户参数,并封装到User
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            json = userService.register(user);
        } else {
            //验证码错误
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", "errorCode");
            json = JSON.toJSONString(map);
        }
        response.getWriter().write(json);
    }

    /**
     * 校验手机号(是否被注册)
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void validatePhone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取请求参数
        String phone = request.getParameter("phone");
        //2.业务处理
        String json = userService.validatePhone(phone);
        //3.给出响应
        response.getWriter().write(json);
    }

    /**
     * 获取手机验证码(发短信)
     *
     * @param request
     * @param response
     */
    public void sendMessage(HttpServletRequest request, HttpServletResponse response) {
        //1.获取手机号
        String phone = request.getParameter("phone");
        //2.生成短信验证码(模拟调用第三方发短信)
//        String code = userService.sendMessage(phone);
        String code = "";
        Random rd = new Random();
        for (int i = 0; i < 6; i++) {
            code += rd.nextInt(10);
        }
        System.out.println("code:" + code);
        //3.保存到session(注册时候比对用户输入的短信验证码是否正确)
        request.getSession().setAttribute("code", code);
        //4.调用工具发送短信给用户
    }

    /**
     * 退出
     *
     * @param request
     * @param response
     */
    public void loginOut(HttpServletRequest request, HttpServletResponse response) {
        //销毁session中保存的用户信息
        request.getSession().invalidate();
    }


}
