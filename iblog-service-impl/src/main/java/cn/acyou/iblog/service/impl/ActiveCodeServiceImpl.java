package cn.acyou.iblog.service.impl;

import cn.acyou.iblog.exception.BussinessException;
import cn.acyou.iblog.mappers.ActiveCodeMapper;
import cn.acyou.iblog.service.ActiveCodeService;
import cn.acyou.iblog.utility.MailUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author youfang
 * @create 2018-02-08 22:49
 */
@Service("activeCodeService")
public class ActiveCodeServiceImpl implements ActiveCodeService{

    @Resource
    private ActiveCodeMapper activeCodeMapper;

    /**
     * 保存激活码
     */
    public String saveActiveCode(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new BussinessException("邮箱地址不能为空！");
        }
        try {
            String code = MailUtil.send_mail(email);
            activeCodeMapper.addActiveCode(email, code);
            return "邮件发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussinessException("邮件发送失败！");
        }

    }

    /**
     * 根据邮箱查找激活码
     * @param email
     * @param email_code
     * @return
     */
    public String findActiveCodeByEmail(String email, String email_code) {
        if (email == null || email.trim().isEmpty()) {
            //throw new ActiveCodeNotFoundException("邮箱地址不能为空！");
            return "false";
        }
        List<String> activeCodes = activeCodeMapper.findActiveCodeByEmail(email);
        if (activeCodes == null) {
            //throw new ActiveCodeNotFoundException("请先发送验证码！");
            return "false";
        }
        //为了忽略大小写
        for (String activeCode : activeCodes) {
            if (activeCode.toUpperCase().equals(email_code.toUpperCase())) {
                return "true";
            }
        }
        return "false";
    }
}
