package cn.acyou.iblog.controller;

import cn.acyou.utility.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试学习；本类没有实际作用
 * <p>title：DemoController</p>
 * @author youfang
 * @date 2017年8月29日 下午2:43:49
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    /**
     * 用于测试getAttribute和getParameter的区别
     * @param username
     * @param password
     * @param request
     * @return
     * 结论：
     * getAttribute表示从request范围取得设置的属性，必须要先setAttribute设置属性，才能通过getAttribute来取得，
     * 设置与取得的为Object对象类型两个Web组件之间为转发关系时，可以通过set绑定数据；
     *
     * getParameter表示接收参数，参数为页面提交的参数.
     * 因此这个并没有设置参数的方法（没有setParameter），而且接收参数返回的不是Object，而是String类型
     */
    @RequestMapping("/gettest.do")
    @ResponseBody
    public JsonResult demo1(String username, String password, HttpServletRequest request){
        System.out.println("表单输入："+username+","+password);
        request.setAttribute("username", "我就是666");
        Object s = request.getAttribute("username");
        System.out.println("getAttribute:"+s);
        String ss = request.getParameter("username");//对应input中的name属性
        System.out.println("getParameter:"+ss);
        return new JsonResult("测试代码");
    }

    @RequestMapping(value = "/getXml.do",produces = {"application/xml; charset=UTF-8"})
    @ResponseBody
    public JsonResult demo1(){
        return new JsonResult("测试代码");
    }


}
