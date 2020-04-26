package com.bilibili.domain;

import java.util.List;

/**
 * @Classname
 * @Description
 * @Date 2020/4/23 16:12
 * @Create by gt
 */
public class PageBean {
    private int currentPage; //当前页码数
    private int prePage; //上一页
    private int nextPage; //下一页
    private int pageSize; //每页显示的数量
    private int totalCount; //数据的总数量
    private int totalPage; //总页码数
    private List<Route> routeList; //分页页面需要的旅游线路信息

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }
}
