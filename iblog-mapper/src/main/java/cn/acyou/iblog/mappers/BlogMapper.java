package cn.acyou.iblog.mappers;

import cn.acyou.iblog.model.Blog;
import cn.acyou.iblog.so.BlogSo;
import cn.acyou.iblog.vo.PigeonholeVo;

import java.util.List;

/**
 * @author youfang
 * @date 2018-03-03 23:46
 **/
public interface BlogMapper {
    /**
     * 获取归档信息
     * @param blogSo so
     * @return 归档List
     */
    List<PigeonholeVo> getPigeonholeVoListByUid(BlogSo blogSo);

    List<Blog> getBlogList(BlogSo blogSo);
}
