package cn.acyou.iblog.service.impl;


import cn.acyou.iblog.exception.BussinessException;
import cn.acyou.iblog.mappers.UserMapper;
import cn.acyou.iblog.model.User;
import cn.acyou.iblog.service.UserService;
import cn.acyou.iblog.so.UserSo;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录
     */
    public User login(String username, String password) {
        User user = userMapper.findUserByUserName(username);
        //查找不到用户的时候说明用户不存在
        if (user == null) {
            throw new BussinessException("用户名错误");
        }
        //只要有引用类型变量；考虑是否有null的情况；
        //当一个引用类型变量其值为空的时候，访问它的属性或者方法会出现空指针异常；
        //确保它一定引用对象
        if (username == null || username.trim().isEmpty()) {
            throw new BussinessException("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new BussinessException("密码不能为空");
        }
        String salt = "小星星";
        String md5Password = DigestUtils.md5Hex(salt + password);
        if (!(user.getPassword().equals(md5Password))) {
            throw new BussinessException("密码错误");
        }
        return user;
    }

    /**
     * 检测邮箱是否已经注册；
     * 返回字符串：true/false
     */
    public String checkEmail(String email) {
        String result = userMapper.findUserByEmail(email);
        if (result == null) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 用户注册
     */
    public User registUser(String email, String username, String password, String confirm_password, String email_code) {
        if (email == null || email.trim().isEmpty() || username == null || username.trim().isEmpty()) {
            throw new BussinessException("邮箱/用户名不能为空");
        }
        String message = userMapper.findUserByEmail(email);
        if ("true".equals(message)) {
            throw new BussinessException("该用户已经存在");
        }
        if (!confirm_password.equals(password)) {
            throw new BussinessException("密码不一致");
        }
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        String salt = "小星星";
        String md5Password = DigestUtils.md5Hex(salt + password);
        user.setPassword(md5Password);
        int a = userMapper.addUser(user);
        System.out.println(a);
        return user;
    }

    @Override
    public Map<Integer, User> getUserMapByIds(UserSo userSo) {
        List<User> userList = userMapper.getUserListByIds(userSo);
        Map<Integer, User> userMap = Maps.newHashMapWithExpectedSize(userList.size());
        for (User user : userList) {
            userMap.put(user.getId(), user);
        }
        return userMap;
    }
}
