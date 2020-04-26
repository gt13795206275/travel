package com.bilibili;

import com.bilibili.domain.Route;
import com.bilibili.domain.User;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 13:05
 * @Create by gt
 */
public class Demo<T> {
    private T obj;

    public Demo() {
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public static void main(String[] args) {
        Demo<User> demo1 = new Demo<User>();
        Demo<Route> demo2 = new Demo<Route>();
        User user = new User();
        Route route = new Route();
        demo1.setObj(user);
        demo2.setObj(route);

        User obj = demo1.getObj();
        Route obj1 = demo2.getObj();
        System.out.println(obj);
        System.out.println(obj1);
    }
}
