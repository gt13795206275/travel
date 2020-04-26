package com.bilibili.dao;

import com.bilibili.domain.Category;

import java.util.List;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 14:11
 * @Create by gt
 */
public interface CategoryDao {
    List<Category> findAllCategory();

}
