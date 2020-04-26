package com.bilibili.dao;

import com.bilibili.domain.Favorite;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 23:51
 * @Create by gt
 */
public interface FavoriteDao {
    Favorite findFavoriteByRidAndUid(int rid, int uid);

    void save(int rid, int uid, String date, JdbcTemplate jdbcTemplate);
}
