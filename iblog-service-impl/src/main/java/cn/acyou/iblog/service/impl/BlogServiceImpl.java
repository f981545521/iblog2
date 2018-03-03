package cn.acyou.iblog.service.impl;

import cn.acyou.iblog.mappers.BlogMapper;
import cn.acyou.iblog.service.BlogService;
import cn.acyou.iblog.so.BlogSo;
import cn.acyou.iblog.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youfang
 * @date 2018-03-04 0:02
 **/
@Service("blogService")
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<BlogVo> getBlogVoListByUid(BlogSo blogSo) {
        return blogMapper.getBlogVoList(blogSo);
    }
}
