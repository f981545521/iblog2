package cn.acyou.iblog.controller;

import cn.acyou.iblog.model.User;
import cn.acyou.iblog.service.*;
import cn.acyou.iblog.utility.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userServie;

    @Resource
    public ActiveCodeService activeCodeService;

    /**
     * 登录方法控制器
     */
    @RequestMapping(value = "/login.do", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult login(String username, String password, HttpSession session) {
        User user = userServie.login(username, password);
        //登录成功之后；保存session
        session.setAttribute("loginUser", user);
        return new JsonResult(user);

    }

    /**
     * 登录方法控制器
     */
    @RequestMapping(value = "/sendCode.do", method = {RequestMethod.GET})
    @ResponseBody
    public String sendCode(String email) {
        String message = activeCodeService.saveActiveCode(email);
        return message;

    }

    /**
     * 检查用户名是否已经注册
     */
    @RequestMapping(value = "/checkMail.do", method = {RequestMethod.GET})
    @ResponseBody
    public String checkEmail(String email) {
        return userServie.checkEmail(email);
    }

    /**
     * 根据邮箱检测激活码
     */
    @RequestMapping(value = "/checkActiveCode.do", method = {RequestMethod.GET})
    @ResponseBody
    public String checkActiveCode(String email, String email_code) {
        return activeCodeService.findActiveCodeByEmail(email, email_code);

    }


    /**
     * 接收用户发过来的邮箱调用DAO存入数据库
     */
    @RequestMapping(value = "/saveActiveCode.do", method = {RequestMethod.GET})
    @ResponseBody
    public JsonResult saveActiveCode(String email) {
        String message = activeCodeService.saveActiveCode(email);
        System.out.println(message);
        return new JsonResult(message);
    }

    /**
     * 注册方法控制器
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/register.do", method = {RequestMethod.POST})
    @ResponseBody
    public Object register(String email, String username, String password, String confirm_password, String email_code) {
        User user = userServie.registUser(email, username, password, confirm_password, email_code);
        return new JsonResult(user);

    }

    /**
     * 在其他控制器方法执行出现异常的时候，执行异常处理方法HandlException
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object HandleException(Exception e) {
        e.printStackTrace();
        return new JsonResult(e);
    }


}
