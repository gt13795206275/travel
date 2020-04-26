package com.bilibili.service.impl;

import com.alibaba.fastjson.JSON;
import com.bilibili.dao.RouteDao;
import com.bilibili.domain.PageBean;
import com.bilibili.domain.Route;
import com.bilibili.domain.RouteImg;
import com.bilibili.service.RouteService;
import com.bilibili.utils.BeansFactory2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 15:39
 * @Create by gt
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = BeansFactory2.getBeans("routeDao");

    @Override
    public String findChoiceRoute() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Route> humanRouteList = routeDao.findHumanRoute();//人气路线
        List<Route> themeRouteList = routeDao.findThemeRoute();//主题路线
        List<Route> newestRouteList = routeDao.findNewestRoute();//最新路线
        map.put("humanRoute", humanRouteList);
        map.put("themeRoute", themeRouteList);
        map.put("newestRoute", newestRouteList);
        return JSON.toJSONString(map);
    }

    @Override
    public String findRouteByPage(int cid, int currentPage, int pageSize, String searchValue) {
        PageBean pageBean = new PageBean();
        //1.当前页码数
        pageBean.setCurrentPage(currentPage);
        //2.上一页
        pageBean.setPrePage(currentPage - 1);
        //3.下一页
        pageBean.setNextPage(currentPage + 1);
        //4.每页数量
        pageBean.setPageSize(pageSize);
        //5.总量
        int totalCount = routeDao.findRouteByCid(cid, searchValue);
        //6.页码总数
        int totalPage = (int) Math.ceil(1.0 * totalCount / pageSize);
        //7.旅游线路信息
        int start = (currentPage - 1) * pageSize;
        List<Route> routeList = routeDao.findRouteByCidAndPage(cid, start, pageSize, searchValue);
        pageBean.setRouteList(routeList);
        return JSON.toJSONString(pageBean);
    }

    @Override
    public String findRouteDetail(int rid) {
        List<RouteImg> routeImgList = routeDao.findRouteImgByRid(rid);
        Route route = routeDao.findRouteDetailByRid(rid);
        route.setRouteImgList(routeImgList);
        return JSON.toJSONString(route);
    }
}
