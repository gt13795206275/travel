package com.bilibili.utils;

import java.util.ResourceBundle;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 12:22
 * @Create by gt
 */
public class BeansFactory {
    /**
     * 工厂模式:
     * 将原有new对象的方式替换成(配置+反射机制)的方式,由统一的一个方法来提供整个程序中需要的javaBean
     * 优点:
     * 1.实现程序中javaBean的创建和使用的分离
     * 2.可以对程序中所有的javaBean进行统一管理
     * 单例模式:一个class类,在内存中只存在一个对象,节省开销!
     */
    public static Object getBean(String beanName) {
        Object obj = null;
        //1.读取配置文件
        ResourceBundle rb = ResourceBundle.getBundle("beans");
        //2.获取配置中指定的字节码文件的路径
        String classPath = rb.getString(beanName);
        //3.反射机制创建对象
        try {
            obj = Class.forName(classPath).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
