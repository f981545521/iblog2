package cn.acyou.iblog.service.impl;

import cn.acyou.iblog.model.Sort;
import cn.acyou.iblog.service.MainService;
import cn.acyou.iblog.service.SortService;
import cn.acyou.iblog.vo.PageVo;
import cn.acyou.iblog.vo.SortVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页
 * @author youfang
 * @date 2018-03-03 22:34
 **/
@Service("mainService")
public class MainServiceImpl implements MainService{

    @Autowired
    private SortService sortService;
    @Override
    public PageVo getinitPage(Integer id) {
        PageVo pageVo = new PageVo();
        List<SortVo> sortList = sortService.getSortVoListByUid(id);
        pageVo.setSortList(sortList);
        return pageVo;
    }
}
