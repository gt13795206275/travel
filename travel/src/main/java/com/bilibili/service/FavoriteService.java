package com.bilibili.service;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 23:16
 * @Create by gt
 */
public interface FavoriteService {
    String checkRouteIsFavorite(int rid, int uid);

    String addFavorite(int rid, int uid);
}
