package com.bilibili.web;

import com.bilibili.service.CategoryService;
import com.bilibili.utils.BeansFactory2;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 14:20
 * @Create by gt
 */
@WebServlet("/categoryServlet")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = BeansFactory2.getBeans("categoryService");

    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //业务处理
        String json = categoryService.findAllCategory();
        //响应
        response.getWriter().write(json);
    }
}
