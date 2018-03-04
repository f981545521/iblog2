package cn.acyou.iblog.service.impl;

import cn.acyou.iblog.mappers.BlogMapper;
import cn.acyou.iblog.mappers.UserMapper;
import cn.acyou.iblog.model.Blog;
import cn.acyou.iblog.model.Sort;
import cn.acyou.iblog.model.User;
import cn.acyou.iblog.orika.OrikaMapper;
import cn.acyou.iblog.service.BlogService;
import cn.acyou.iblog.service.SortService;
import cn.acyou.iblog.service.UserService;
import cn.acyou.iblog.so.BlogSo;
import cn.acyou.iblog.so.SortSo;
import cn.acyou.iblog.so.UserSo;
import cn.acyou.iblog.vo.BlogVo;
import cn.acyou.iblog.vo.PigeonholeVo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author youfang
 * @date 2018-03-04 0:02
 **/
@Service("blogService")
public class BlogServiceImpl implements BlogService {
    @Resource
    protected OrikaMapper orikaMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SortService sortService;

    @Override
    public List<PigeonholeVo> getPigeonholeVoListByUid(BlogSo blogSo) {
        return blogMapper.getPigeonholeVoListByUid(blogSo);
    }

    @Override
    public List<BlogVo> getBlogVoListByUid(BlogSo blogSo) {
        List<Blog> blogList = blogMapper.getBlogList(blogSo);
        List<BlogVo> blogVoList = orikaMapper.convertList(blogList, BlogVo.class);
        List<Integer> idList = Lists.transform(blogVoList, new Function<BlogVo, Integer>() {
            @Override
            public Integer apply(BlogVo blogVo) {
                return blogVo.getIdUser();
            }
        });
        UserSo userSo = new UserSo();
        userSo.setIdList(idList);
        Map<Integer, User> userMap = userService.getUserMapByIds(userSo);
        for (BlogVo blogVo : blogVoList){
            blogVo.setAuthor(userMap.get(blogVo.getIdUser()).getNickname());
        }


        List<Integer> idSortList = Lists.transform(blogVoList, new Function<BlogVo, Integer>() {
            @Override
            public Integer apply(BlogVo blogVo) {
                return blogVo.getIdSort();
            }
        });
        SortSo sortSo = new SortSo();
        sortSo.setIdList(idSortList);
        Map<Integer, Sort> sortMap = sortService.getSortMapByIds(sortSo);
        for (BlogVo blogVo : blogVoList){
            blogVo.setSortName(sortMap.get(blogVo.getIdSort()).getSortName());
        }
        return blogVoList;
    }


}
