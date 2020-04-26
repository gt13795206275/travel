package com.bilibili.service.impl;

import com.alibaba.fastjson.JSON;
import com.bilibili.dao.FavoriteDao;
import com.bilibili.dao.RouteDao;
import com.bilibili.domain.Favorite;
import com.bilibili.service.FavoriteService;
import com.bilibili.utils.BeansFactory2;
import com.bilibili.utils.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 23:19
 * @Create by gt
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = BeansFactory2.getBeans("favoriteDao");
    private RouteDao routeDao = BeansFactory2.getBeans("routeDao");

    @Override
    public String checkRouteIsFavorite(int rid, int uid) {
        //判断用户是否收藏过该路线
        Favorite favorite = favoriteDao.findFavoriteByRidAndUid(rid, uid);
        Map<String, String> map = new HashMap<String, String>();
        if (favorite == null) {
            //未收藏
            map.put("message", "noFavorite");
        } else {
            //已收藏
            map.put("message", "alreadyFavorite");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 旅游路线收藏
     * 往收藏表保存数据,同时更新路线表中的count字段值+1
     *
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public String addFavorite(int rid, int uid) {
        /*
        jdbc中 事务的开启
             mysql数据库中事务事务默认开启的，一条sql语句就是一个事务，
             在执行sql之前，开启事务，在sql执行之后，自动提交事务。
            Connection.setAutoCommit(false);关闭自动提交，手动提交事务。
            Connection.commit();
            Connection.rollback();

            注意：
                事务需要在service层开启。service层必然需要获取Connection连接对象
                dao层因为要操作数据库，dao层的底层也需要用到Conenction对象
                问题：service层和dao层的连接对象可不可以不是同一个连接？
                    不行！！

                1.service和dao层的连接对象Connection必须是同一个！！
                2.只能使用jdbcTemplate所提供的事务的操作方式。
                    jdbcTemplate操作事务的流程
                    a.创建jdbcTemplate对象
                    b.开启jdbcTemplate的事务管理器
                    c.获取连接对象
                    d.开启事务，提交事务，回滚事务。。。
         */

        String json = "";
        Map<String, String> map = new HashMap<String, String>();

        //a.创建jdbcTemplate对象
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        //b.开启jdbcTemplate的事务管理器,实现连接对象和当前线程的绑定,ThreadLocal实现同一线程内数据的共享
        TransactionSynchronizationManager.initSynchronization();
        //c.获取与当前线程绑定的连接对象
        Connection connection = DataSourceUtils.getConnection(JDBCUtils.getDataSource());

        try {
            //开启事务
            connection.setAutoCommit(false);//关闭自动提交事务,开启手动提交事务
            //1.tab_favorite表插入数据
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(new Date());
            favoriteDao.save(rid, uid, date, jdbcTemplate);
            //2.修改tab_route表中的count字段
            routeDao.updateRouteCountByRid(rid, jdbcTemplate);
            //3.重新查询tab_route表的count字段(旅游路线详情页要展示该路线的收藏次数)
            int count = routeDao.findRouteCountByRid(rid, jdbcTemplate);

            //提交事务
            connection.commit();
            map.put("message", "success");
            map.put("count", count + "");
        } catch (Exception e) {
            map.put("message", "fail");
            //事务回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            //释放
            try {
                connection.setAutoCommit(true);//重新设置为事务自动提交
            } catch (SQLException e) {
                e.printStackTrace();
            }
            TransactionSynchronizationManager.clearSynchronization();
        }

        return JSON.toJSONString(map);
    }
}
