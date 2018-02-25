package cn.acyou.iblog.service;


import cn.acyou.iblog.model.User;

/**
 * User业务层接口
 * @author youfang
 *	登录功能；登录成功返回用户信息，
 *	登录失败；抛出异常；
 */
public interface UserService {
	/**
	 * 用户登录方法
	 * @param username	用户名
	 * @param password	密码
	 * @return	返回用户

	 */
	User login(String username, String password);
	
	/**检查邮箱是否已经注册*/
	String checkEmail(String email);
	
	/**用户注册*/
	User registUser(String email, String username, String password, String confirm_password, String email_code);
}
