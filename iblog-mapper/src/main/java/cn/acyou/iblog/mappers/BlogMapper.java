package cn.acyou.iblog.mappers;

import cn.acyou.iblog.so.BlogSo;
import cn.acyou.iblog.vo.BlogVo;

import java.util.List;

/**
 * @author youfang
 * @date 2018-03-03 23:46
 **/
public interface BlogMapper {
    List<BlogVo> getBlogVoList(BlogSo blogSo);
}
