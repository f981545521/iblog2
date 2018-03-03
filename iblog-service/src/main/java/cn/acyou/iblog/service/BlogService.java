package cn.acyou.iblog.service;

import cn.acyou.iblog.so.BlogSo;
import cn.acyou.iblog.vo.BlogVo;

import java.util.List;

/**
 * @author youfang
 * @date 2018-03-04 0:01
 **/
public interface BlogService {

    List<BlogVo> getBlogVoListByUid(BlogSo blogSo);
}
