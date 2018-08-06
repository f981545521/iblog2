package cn.acyou.iblog.controller;

import cn.acyou.iblog.constant.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author youfang
 * @date 2018-02-09 11:06
 **/
@Controller
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass().getName());
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;

    /**
     * 用于处理Date类型参数处理
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.DEFAULT_DATE_FORMAT_PATTERN);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
