package cn.acyou.iblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author youfang
 * @date 2018-02-09 11:05
 **/
@Controller
@RequestMapping("/hello")
public class HelloController extends BaseController{

    @RequestMapping("/hellojsp")
    public ModelAndView getHelloPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/hello/hello");
        return  mv;
    }
}
