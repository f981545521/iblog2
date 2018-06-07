package cn.acyou.iblog.controller;

import cn.acyou.iblog.service.CommonService;
import cn.acyou.iblog.utility.ImageUtil;
import cn.acyou.iblog.utility.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 公共模块
 *
 * @author youfang
 * @date 2017年7月17日 下午8:55:20
 */
@Controller
@RequestMapping("common")
public class CommonController extends BaseController{

    @Resource
    private CommonService commonService;

    @RequestMapping(value = "weather.do", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult showWeather(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        logger.warn("当前访问用户IP" + ip);
        List<Map<String, Object>> list = commonService.getWeather(ip);
        logger.warn(list.toString());
        return new JsonResult(list);
    }


    @RequestMapping(value = "validateImg.do", method = {RequestMethod.GET})
    @ResponseBody
    public void getValidateImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建验证码及图片
        Object[] objs = ImageUtil.createImage();
        //将验证码存入session
        HttpSession sn = request.getSession();
        sn.setAttribute("imgCode", objs[0]);
        //将图片输出给浏览器
        response.setContentType("image/png");
        //获取字节输出流,该流由服务器创建,其目标就是当前访问的那个浏览器.
        OutputStream os = response.getOutputStream();
        BufferedImage img=(BufferedImage) objs[1];
        ImageIO.write(img, "png", os);
        os.close();
    }


}