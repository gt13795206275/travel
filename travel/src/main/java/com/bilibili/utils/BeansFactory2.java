package com.bilibili.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 13:17
 * @Create by gt
 */
public class BeansFactory2 {
    /**
     * 工厂模式优化(泛型+Map)
     */

    //创建一个集合,用来保存工厂产生的javaBean对象
    private static Map<String, Object> beansMap = new HashMap<String, Object>();

    public static <T> T getBeans(String beanName) {
        Object obj = null;
        //优先从map集合中获取javaBean对象
        obj = beansMap.get(beanName);
        if (obj == null) {
            //加载配置文件
            ResourceBundle rb = ResourceBundle.getBundle("beans");
            //获取配置文件中指定的字节码文件的路径
            String classPath = rb.getString(beanName);
            //反射机制创建对象
            try {
                obj = Class.forName(classPath).newInstance();
                //保存到map集合中,方便下次直接使用
                beansMap.put(beanName,obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return (T)obj;
    }
}
