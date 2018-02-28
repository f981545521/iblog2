package cn.acyou.iblog.controller;

import cn.acyou.iblog.service.CommonService;
import cn.acyou.iblog.utility.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class CommonController {

    @Resource
    private CommonService commonService;
    //Log4J日志记录器
    private static final Logger logger = Logger.getLogger(CommonController.class);

    @RequestMapping("weather.do")
    @ResponseBody
    public JsonResult showWeather(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        logger.warn("当前访问用户IP" + ip);
        //List<Map<String,Object>>list=commonService.getWeather(ip);
        //测试环境写死IP：江苏南京
        List<Map<String, Object>> list = commonService.getWeather("114.222.237.206");
        logger.warn(list);
        return new JsonResult(list);
    }
}