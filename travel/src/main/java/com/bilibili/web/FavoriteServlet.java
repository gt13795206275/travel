package com.bilibili.web;

import com.alibaba.fastjson.JSON;
import com.bilibili.domain.User;
import com.bilibili.service.FavoriteService;
import com.bilibili.utils.BeansFactory2;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 23:07
 * @Create by gt
 */
@WebServlet("/favoriteServlet")
public class FavoriteServlet extends BaseServlet {
    private FavoriteService favoriteService = BeansFactory2.getBeans("favoriteService");

    /**
     * 旅游线路详情页,收藏按钮显示.(高亮显示收藏/未收藏按钮)
     *
     * @param request
     * @param response
     */
    public void checkRouteIsFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = "";
        //首先校验用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            //未登录
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", "noLogin");
            json = JSON.toJSONString(map);
        } else {
            //已登录
            //获取请求参数
            int rid = Integer.parseInt(request.getParameter("rid"));
            int uid = user.getUid();
            //业务处理
            json = favoriteService.checkRouteIsFavorite(rid, uid);
        }
        //响应
        response.getWriter().write(json);
    }

    /**
     * 添加收藏
     *
     * @param request
     * @param response
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //首先判断用户是否登录
        String json = "";
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            //未登录
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", "noLogin");
            json = JSON.toJSONString(map);
        } else {
            //已登录
            //获取请求参数
            int rid = Integer.parseInt(request.getParameter("rid"));
            //业务处理
            json = favoriteService.addFavorite(rid, user.getUid());
        }
        //响应
        response.getWriter().write(json);
    }


}
