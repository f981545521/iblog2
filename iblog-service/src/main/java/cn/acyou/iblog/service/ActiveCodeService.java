package cn.acyou.iblog.service;

/**
 * 邮箱注册码服务层处理方法
 */
public interface ActiveCodeService {
    /**
     * 保存验证码到数据库
     * @param email	页面传入的email地址
     */
    String saveActiveCode(String email);

    /**
     * 按照邮箱查找注册码
     * @param email
     * @param email_code
     * @return
     */
    String findActiveCodeByEmail(String email,String email_code);

}
