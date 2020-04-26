package com.bilibili.web;

import com.bilibili.service.UserService;
import com.bilibili.utils.BeansFactory;
import com.bilibili.utils.BeansFactory2;
import org.springframework.beans.factory.BeanFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 10:40
 * @Create by gt
 */
@WebServlet("/DemoServlet")
public class DemoServlet extends BaseServlet {

    /**
     * 面向接口编程的好处,解耦,方便程序功能拓展,不用修改源码
     * 不使用直接new的方式创建对象,而是采用配置文件+反射的方式创建对象
     * 工厂模式
     */

    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = "";
        //获取参数
        String phone = request.getParameter("username");
        String password = request.getParameter("password");
//        //读取配置文件,反射机制创建对象
//        ResourceBundle rb = ResourceBundle.getBundle("beans");
//        String classPath = rb.getString("classPath");
//
//        //反射创建对象
//        try {
//            UserService userService = (UserService) Class.forName(classPath).newInstance();
//            json = userService.login(phone, password, request);
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        //响应
//        response.getWriter().write(json);

        UserService userService = BeansFactory2.<UserService>getBeans("userService");
        json = userService.login(phone, password, request);
        //响应
        response.getWriter().write(json);
    }
}
