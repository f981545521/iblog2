package cn.acyou.iblog.controller;

import cn.acyou.iblog.mappers.BossMapper;
import cn.acyou.iblog.model.test.Boss;
import cn.acyou.iblog.service.BossService;
import cn.acyou.iblog.utility.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author youfang
 * @date 2018-02-09 11:05
 **/
@Controller
@RequestMapping("/hello")
public class HelloController extends BaseController{

    @Autowired
    private BossService bossService;
    @Autowired
    private BossMapper bossMapper;

    @RequestMapping(value = "/setSession",method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult setSession(HttpServletRequest request){
        request.getSession().setAttribute("user", "xiaofeifei");
        return new JsonResult("设置成功");
    }
    @RequestMapping(value = "/getSession",method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult getSession(HttpServletRequest request){
        Object obj = request.getSession().getAttribute("user");
        return new JsonResult((String) obj);
    }

    @RequestMapping(value = "/hellojsp",method = {RequestMethod.GET})
    public ModelAndView getHelloPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/hello/hello");
        List<Boss> tBossList = bossService.getAllBoss();
        return  mv;
    }

    @RequestMapping(value = "/addBoss",method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult addBoss(@RequestBody Boss boss){
        bossService.addBoss(boss);
        return new JsonResult("操作成功");
    }
    @RequestMapping(value = "/realAddBoss",method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult realAddBoss(Boss boss){
        bossMapper.addBoss(boss);
        return new JsonResult("操作成功");
    }

    @RequestMapping(value = "/boss", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult showBoss(@RequestBody Boss boss){
        return new JsonResult(boss);
    }





}
