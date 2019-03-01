package cn.acyou.iblog.controller;

import cn.acyou.iblog.service.AttachmentService;
import cn.acyou.iblog.utility.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 附件模块控制器
 *
 * @author youfang
 * @date 2017年7月22日 下午5:21:56
 */
@Controller
@RequestMapping("attachment")
public class AttachmentController extends BaseController{
    /**
     * service
     */
    @Autowired
    private AttachmentService attachmentService;

    /**
     * 上传文件
     */
    @RequestMapping(value = "/upload.do", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult upload(MultipartFile userfile, HttpServletRequest request, Integer uid, Integer bid, String description) {
        try {
            attachmentService.saveAttahment(userfile, request, uid, bid, description);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new JsonResult("false");
        }
        return new JsonResult("true");
    }

    /**
     * 列出附件
     *
     * @param uid    用户ID
     * @param pageId 当前页
     * @return
     */
    @RequestMapping(value = "/page.do", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult page(Integer uid, Integer pageId) {
        //由于分页是从0开始；而页数是从1开始，需要将传入的页数-1
        pageId = pageId - 1;
        List<Map<String, Object>> list = attachmentService.listAttachments(uid, pageId);
        return new JsonResult(list);
    }

    /**
     * 列出一共有多少记录
     *
     * @param uid 用户ID
     * @return 总记录数
     */
    @RequestMapping(value = "/pageTotal.do", method = {RequestMethod.GET})
    @ResponseBody
    public int pageTotal(int uid) {
        return attachmentService.countAttachmentByUid(uid);
    }

}
