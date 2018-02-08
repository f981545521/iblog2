package cn.acyou.iblog.utility;

import cn.acyou.iblog.exception.EmailErrorException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * 邮件工具类
 */
public class MailUtil {
    /**
     * 发送邮件
     *
     * @param to   给谁发
     * @param text 发送内容
     * @throws UnsupportedEncodingException
     */
    public static void send_mail(String to, String text) throws Exception {
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        //发送邮件服务器
        properties.put("mail.smtp.host", "smtp.126.com");
        //发送端口
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码
                return new PasswordAuthentication("iblog_admin@126.com", "iblog96321");
            }
        });

        //创建邮件对象
        Message message = new MimeMessage(session);
        //为了防止邮件主题！邮件昵称乱码！使用enc编码方式！！
        @SuppressWarnings("restriction")
        sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
        //设置自定义发件人昵称  
        String nick = "";
        try {
            String nickText = "iblog用户中心";
            nick = javax.mail.internet.MimeUtility.encodeText(MimeUtility.encodeText(nickText, "utf-8", "B"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置发件人
        message.setFrom(new InternetAddress(nick + " <iblog_admin@126.com>"));
        //设置收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

        ////解决Linux上主题乱码问题
        String subject = "[iblog]激活邮箱账号";

        //message.setSubject("=?utf-8?B?"+enc.encode(subject.getBytes())+"?=");
        message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
        //放弃直接使用字符串形式->设置主题
        //message.setSubject("[iblog]激活邮箱账号");
        //设置邮件正文  第二个参数是邮件发送的类型
        message.setContent(text, "text/html;charset=UTF-8");
        //发送一封邮件
        Transport.send(message);
    }

    /**
     * 生成验证码
     *
     * @param length 验证码长度
     * @return String 验证码
     */
    public static String generateMailCode(int length) {
        char[] codes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z'};
        String code = "";
        for (int i = 0; i < length; i++) {
            Random ran = new Random();
            int type = ran.nextInt(codes.length);
            code = code + codes[type];
        }
        return code;
    }

    /**
     * 重载发送邮件方法!!
     *
     * @param to 收件人
     * @throws UnsupportedEncodingException
     */
    public static String send_mail(String to) throws UnsupportedEncodingException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String code;
        try {
            code = MailUtil.generateMailCode(5);
            String content = "<h3>尊敬的用户,您好:</h3>"
                    + "您于" + fmt.format(new Date()) + "提交了邮箱验证申请.<br/><br/>"
                    + "您在iblog注册的验证码是:<strong style='color: red;''>" + code + "</strong><br/><br/>"
                    + "欢迎使用本站！本邮件由系统自动发出，请勿直接回复！";
            MailUtil.send_mail(to, content);
            System.out.println("邮件发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailErrorException("邮件发送失败!");
        }
        return code;
    }
}