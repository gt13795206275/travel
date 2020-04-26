package com.bilibili.dao.impl;

import com.bilibili.dao.CategoryDao;
import com.bilibili.domain.Category;
import com.bilibili.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 14:11
 * @Create by gt
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAllCategory() {
        String sql = "select * from tab_category";
        List<Category> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return list;
    }
}
