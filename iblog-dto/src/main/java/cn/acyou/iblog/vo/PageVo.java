package cn.acyou.iblog.vo;

import cn.acyou.iblog.model.Blog;


import java.util.List;

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
    List<PigeonholeVo> pigeonholeList;

    /**
     * 博客列表
     */
    List<BlogVo> blogVoList;

    public List<SortVo> getSortList() {
        return sortList;
    }

    public void setSortList(List<SortVo> sortList) {
        this.sortList = sortList;
    }

    public List<PigeonholeVo> getPigeonholeList() {
        return pigeonholeList;
    }

    public void setPigeonholeList(List<PigeonholeVo> pigeonholeList) {
        this.pigeonholeList = pigeonholeList;
    }

    public List<BlogVo> getBlogVoList() {
        return blogVoList;
    }

    public void setBlogVoList(List<BlogVo> blogVoList) {
        this.blogVoList = blogVoList;
    }
}
