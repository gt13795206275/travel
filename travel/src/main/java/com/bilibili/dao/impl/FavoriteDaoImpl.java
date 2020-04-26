package com.bilibili.dao.impl;

import com.bilibili.dao.FavoriteDao;
import com.bilibili.domain.Favorite;
import com.bilibili.utils.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * @Classname
 * @Description
 * @Date 2020/4/23 23:51
 * @Create by gt
 */
public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findFavoriteByRidAndUid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        Favorite favorite = null;
        try {
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("一切正常");
        }
        return favorite;
    }

    @Override
    public void save(int rid, int uid, String date, JdbcTemplate jdbcTemplate) {
        String sql = "insert into tab_favorite values (? , ? , ?)";
        jdbcTemplate.update(sql,rid,date,uid);
    }
}
