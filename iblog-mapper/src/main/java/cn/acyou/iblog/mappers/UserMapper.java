package cn.acyou.iblog.mappers;


import cn.acyou.iblog.model.User;

/**
 * 用户持久层
 */
public interface UserMapper {
	/**按照用户名查找用户*/
	User findUserByUserName(String username);
	/**增加用户*/
	int addUser(User user);
	/**注册时判断邮箱是否存在*/
	String findUserByEmail(String email);
	
	/**按照uid查找用户*/
	User findUserBuUid(Integer uid);
}
