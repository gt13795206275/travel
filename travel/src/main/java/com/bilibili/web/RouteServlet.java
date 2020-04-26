package com.bilibili.web;

import com.bilibili.service.RouteService;
import com.bilibili.utils.BeansFactory2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 15:35
 * @Create by gt
 */
@WebServlet("/RouteServlet")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = BeansFactory2.getBeans("routeService");

    /**
     * 旅游线路精选查询(包括主题路线,热门路线,最新路线)
     *
     * @param request
     * @param response
     */
    public void findChoiceRoute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        //业务处理
        String json = routeService.findChoiceRoute();
        //响应
        response.getWriter().write(json);
    }

    /**
     * 分页查询
     * 1.导航菜单旅游路线列表分页显示
     * 2.搜索分页显示
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void findRouteByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        int cid = Integer.parseInt(request.getParameter("cid"));
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String searchValue = request.getParameter("searchValue");
        //业务处理
        String json = routeService.findRouteByPage(cid, currentPage, pageSize, searchValue);
        //响应
        response.getWriter().write(json);
    }

    /**
     * 旅游路线详情
     * @param request
     * @param response
     */
    public void findRouteDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        int rid = Integer.parseInt(request.getParameter("rid"));
        String json = routeService.findRouteDetail(rid);
        response.getWriter().write(json);
    }

}
