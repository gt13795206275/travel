package com.bilibili.dao.impl;

import com.bilibili.dao.UserDao;
import com.bilibili.domain.User;
import com.bilibili.utils.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Classname
 * @Description
 * @Date 2020/4/22 19:48
 * @Create by gt
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByPhone(String phone) {
        String sql = "select * from tab_user where telephone = ?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), phone);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("只是没有查询到数据，一切正常");
        }

        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user values (null,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday()
        ,user.getSex(),user.getTelephone(),user.getEmail());
    }
}
