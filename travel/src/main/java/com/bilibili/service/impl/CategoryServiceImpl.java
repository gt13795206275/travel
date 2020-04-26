package com.bilibili.service.impl;

import com.alibaba.fastjson.JSON;
import com.bilibili.dao.CategoryDao;
import com.bilibili.dao.impl.CategoryDaoImpl;
import com.bilibili.domain.Category;
import com.bilibili.service.CategoryService;
import com.bilibili.utils.BeansFactory2;
import com.bilibili.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 14:09
 * @Create by gt
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = BeansFactory2.getBeans("categoryDao");

    @Override
    public String findAllCategory() {
        //优先读取缓存(由于分类一般很久不变,而且请求量比较大)
        Jedis jedis = JedisUtils.getJedis();
        String json = jedis.get("AllCategory123");
        if (StringUtils.isEmpty(json)) {
            //读取数据库
            List<Category> categories = categoryDao.findAllCategory();
            //保存到缓存
            json = JSON.toJSONString(categories);
            jedis.set("AllCategory", json);

//            Category category = new Category();
//            category.setCid(123);
//            category.setCname("分类名");
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("cid", Integer.toString(category.getCid()));
//            map.put("cname", category.getCname());
//            jedis.hmset("allCategory", map);

        }

        return json;
    }
}
