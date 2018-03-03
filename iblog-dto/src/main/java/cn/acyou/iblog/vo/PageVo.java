package cn.acyou.iblog.vo;

import cn.acyou.iblog.model.Blog;


import java.util.List;
import java.util.Map;

/**
 * 首页Vo
 * @author youfang
 * @date 2018-03-03 17:12
 **/
public class PageVo extends Vo{
    private static final long serialVersionUID = 4712301900413127488L;
    /**
     * 文章分类
     */
    List<SortVo> sortList;

    /**
     * 文章归档（最近5个月）
     */
    List<BlogVo> pigeonholeList;

    /**
     * 博客列表
     */
    List<Blog> blogList;

    public List<SortVo> getSortList() {
        return sortList;
    }

    public void setSortList(List<SortVo> sortList) {
        this.sortList = sortList;
    }

    public List<BlogVo> getPigeonholeList() {
        return pigeonholeList;
    }

    public void setPigeonholeList(List<BlogVo> pigeonholeList) {
        this.pigeonholeList = pigeonholeList;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }
}
