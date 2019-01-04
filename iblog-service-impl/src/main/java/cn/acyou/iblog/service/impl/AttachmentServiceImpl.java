package cn.acyou.iblog.service.impl;

import cn.acyou.iblog.exception.BusinessException;
import cn.acyou.iblog.mappers.AttachmentMapper;
import cn.acyou.iblog.mappers.UserMapper;
import cn.acyou.iblog.model.Attachment;
import cn.acyou.iblog.model.User;
import cn.acyou.iblog.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
    // 上传文件存储目录（读取配置文件）
    @Value("#{resourceConf.uploadpath}")
    private String upload_directory;
    // 分页的大小（读取配置文件）
    @Value("#{resourceConf.pageSize}")
    private int pageSize;
    @Resource
    private AttachmentMapper attachmentMapper;
    @Resource
    private UserMapper userMapper;

    private final static Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    /**
     * 保存附件
     */
    @Transactional
    public int saveAttahment(MultipartFile userfile, HttpServletRequest request, Integer uid, Integer bid, String description) {
        String fileName = userfile.getOriginalFilename();
        System.out.println(fileName);
        //利用request获取访问的路径；计算出上传的路径
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + upload_directory;
        //创建路径对象
        File dir = new File(uploadPath);
        //如果路径不存在
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //创建文件对象File(目录,文件名);
        File newfile = new File(dir, fileName);
        //直接用原始文件复制到目标文件即可！！
        try {
            userfile.transferTo(newfile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("保存失败！");
        }
        //将记录保存到数据库中
        Attachment att = new Attachment();
        //att.setUid(uid);
        //att.setBid(bid);
        att.setDescription(description);
        att.setFileName(fileName);
        att.setFilePath(uploadPath);
        att.setFileSize(userfile.getSize());
        int n = attachmentMapper.saveAttachment(att);
        return n;
    }

    /**
     * 列出所有笔记本：分页模式
     */
    public List<Map<String, Object>> listAttachments(Integer id, Integer pageId) {
        User user = userMapper.findUserBuId(id);
        if (user == null) {
            throw new BusinessException("没有这个用户");
        }
        int start = pageId * pageSize;
        List<Map<String, Object>> list = attachmentMapper.findAttachementsByUid(id, start, pageSize);
        log.warn(list.toString());
        return list;
    }

    /**
     * 统计自己的附件一共多少：分页
     */
    public int countAttachmentByUid(Integer uid) {
        Integer n = attachmentMapper.countAttachmentByUid(uid);
        return n;
    }


}
