package cn.acyou.iblog.service;

import cn.acyou.iblog.vo.PageVo;

/**
 * @author youfang
 * @date 2018-03-03 17:10
 **/
public interface MainService {

    /**
     * 获取初始页面
     * @param id 用户Id
     * @return PageVo
     */
    PageVo getinitPage(Integer id);


}
