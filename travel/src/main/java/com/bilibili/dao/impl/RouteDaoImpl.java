package com.bilibili.dao.impl;

import com.bilibili.dao.RouteDao;
import com.bilibili.domain.Category;
import com.bilibili.domain.Route;
import com.bilibili.domain.RouteImg;
import com.bilibili.domain.Seller;
import com.bilibili.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 15:40
 * @Create by gt
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Route> findHumanRoute() {
        String sql = "select * from tab_route order by count desc limit 0,4 ";
        List<Route> humanRoute = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return humanRoute;
    }

    @Override
    public List<Route> findThemeRoute() {
        String sql = "select * from tab_route where isThemeTour = 1 limit 0,4";
        List<Route> themeRoute = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return themeRoute;
    }

    @Override
    public List<Route> findNewestRoute() {
        String sql = "select * from tab_route order by rdate desc limit 0,4";
        List<Route> newesRoute = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return newesRoute;
    }

    @Override
    public int findRouteByCid(int cid, String searchValue) {
        String sql = "select count(*) from tab_route where cid = ?";
        if (StringUtils.isNotEmpty(searchValue)) {
            sql += " and rname like ?";
        }
        Integer totalCount = jdbcTemplate.queryForObject(sql, Integer.class, cid, "%" + searchValue + "%");
        return totalCount;
    }

    @Override
    public List<Route> findRouteByCidAndPage(int cid, int start, int pageSize, String searchValue) {
        String sql = "select * from tab_route where cid = ?";
        if (StringUtils.isNotEmpty(searchValue)) {
            sql += " and rname like ?";
        }
        sql += "limit ? , ?";
        List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid, "%" + searchValue + "%", start, pageSize);
        return routes;
    }

    @Override
    public List<RouteImg> findRouteImgByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        List<RouteImg> routeImgList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return routeImgList;
    }

    @Override
    public Route findRouteDetailByRid(int rid) {
        String sql = "select * from tab_route r , tab_seller s ,tab_category c where rid = ? and r.cid = c.cid and r.sid = s.sid";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, rid);
        Route route = new Route();
        Category category = new Category();
        Seller seller = new Seller();
        try {
            BeanUtils.populate(route, map);
            BeanUtils.populate(category, map);
            BeanUtils.populate(seller, map);
            route.setCategory(category);
            route.setSeller(seller);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return route;
    }

    @Override
    public void updateRouteCountByRid(int rid, JdbcTemplate jdbcTemplate) {
        String sql = "update tab_route set count = count+1 where rid = ?";
        jdbcTemplate.update(sql, rid);
    }

    @Override
    public int findRouteCountByRid(int rid, JdbcTemplate jdbcTemplate) {
        String sql = "select count from tab_route where rid = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, rid);
        return count;
    }
}
