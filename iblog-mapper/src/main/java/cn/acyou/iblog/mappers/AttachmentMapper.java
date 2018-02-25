package cn.acyou.iblog.mappers;

import cn.acyou.iblog.model.Attachment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 附件：
 */
public interface AttachmentMapper {
	
	/**	附件列表：
	 * 分页：查看属于自己的所有附件；
	 * */
	List<Map<String,Object>> findAttachementsByUid(@Param("uid") Integer uid, @Param("start") int start, @Param("pageSize") int pageSize);

	/**
	 * 保存附件
	 * @param attachment
	 * @return
     */
	int saveAttachment(Attachment attachment);

	/**
	 * 一共有多少条记录
	 * @param uid
	 * @return
     */
	int countAttachmentByUid(Integer uid);
}
