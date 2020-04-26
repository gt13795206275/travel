package com.bilibili.dao;

import com.bilibili.domain.Route;
import com.bilibili.domain.RouteImg;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 15:40
 * @Create by gt
 */
public interface RouteDao {
    List<Route> findHumanRoute();

    List<Route> findThemeRoute();

    List<Route> findNewestRoute();

    int findRouteByCid(int cid, String searchValue);

    List<Route> findRouteByCidAndPage(int cid, int start, int pageSize, String searchValue);

    List<RouteImg> findRouteImgByRid(int rid);

    Route findRouteDetailByRid(int rid);

    void updateRouteCountByRid(int rid, JdbcTemplate jdbcTemplate);

    int findRouteCountByRid(int rid, JdbcTemplate jdbcTemplate);
}
