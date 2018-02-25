package cn.acyou.iblog.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 附件：业务层接口
 *
 */
public interface AttachmentService {
    /**
     * 保存附件
     */
    int saveAttahment(MultipartFile userfile, HttpServletRequest request, Integer uid, Integer bid, String description);

    /**
     * 列出所有笔记本：分页模式
     */
    List<Map<String, Object>> listAttachments(Integer uid, Integer pageId);

    /**
     * 查看自己一个有多少附件
     */
    int countAttachmentByUid(Integer uid);
}
