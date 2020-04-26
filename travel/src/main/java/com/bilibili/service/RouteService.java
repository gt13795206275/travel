package com.bilibili.service;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 15:38
 * @Create by gt
 */
public interface RouteService {
    String findChoiceRoute();

    String findRouteByPage(int cid, int currentPage,int pageSize,String searchValue);

    String findRouteDetail(int rid);
}
