package cn.acyou.iblog.controller;

import cn.acyou.iblog.utility.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 测试学习；本类没有实际作用
 * <p>title：DemoController</p>
 * @author youfang
 * @date 2017年8月29日 下午2:43:49
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController{

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
    @RequestMapping(value = "/gettest.do",method = {RequestMethod.GET})
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

    @RequestMapping(value = "handler", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult demo2(String name){
        return new JsonResult(name);
    }



    @RequestMapping(value = "/getXml.do",produces = {"application/xml; charset=UTF-8"},method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult demo1(){
        return new JsonResult("测试代码");
    }

    @RequestMapping(value = "404", method = {RequestMethod.GET})
    public ModelAndView get404Page(){
        return new ModelAndView("/error/404");
    }

    @RequestMapping(value = "setSession", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult setSession(HttpServletRequest request, String name){
        HttpSession session = request.getSession();
        session.setAttribute("sessionName", name);
        return new JsonResult("设置成功");
    }
    @RequestMapping(value = "getSession", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult getSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("sessionName");
        return new JsonResult(obj);
    }


    @RequestMapping(value = "startJob", method = {RequestMethod.GET})
    public void startJob() throws Exception{
        String job_name = "动态任务调度";
        System.out.println("【系统启动】开始(每1秒输出一次)...");
        //org.quartz.SchedulerException:  Based on configured schedule, the given trigger will never fire.
        //大概意思是设置的触发器时间比当前时间小，永远不会触发，所以需要修改触发时间
        //QuartzManager.addJob(job_name, QuartzJob.class, "* 15 11 28 6 ? 2018");

    }

}
