package cn.acyou.iblog.controller;

import cn.acyou.iblog.model.test.TBoss;
import cn.acyou.iblog.service.BossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/hellojsp")
    public ModelAndView getHelloPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/hello/hello");
        List<TBoss> tBossList = bossService.getAllBoss();
        return  mv;
    }
}
