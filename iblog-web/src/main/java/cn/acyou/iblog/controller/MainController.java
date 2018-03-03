package cn.acyou.iblog.controller;

import cn.acyou.iblog.service.MainService;
import cn.acyou.iblog.utility.JsonResult;
import cn.acyou.iblog.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author youfang
 * @date 2018-03-03 17:08
 **/
@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/initPage.do", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult getInitPage(){
        PageVo pageVo = mainService.getinitPage(1);
        return new JsonResult(pageVo);
    }

}
