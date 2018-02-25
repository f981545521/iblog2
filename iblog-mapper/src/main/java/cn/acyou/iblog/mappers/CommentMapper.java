package cn.acyou.iblog.mappers;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 评论的DAO层接口
 * @author youfang
 * @Date 2017年7月27日 上午10:52:39
 */
public interface CommentMapper {
	
	/**
	 * Redis使用：查询文章的前5条数据，并加载到redis中。
	 * @param bid 文章ID
	 * @return 评论列表
	 */
	List<Map<String,Object>> findTopFiveByBid(Integer bid);

}
