package cn.acyou.iblog.mappers;

import cn.acyou.iblog.so.ActiveCodeSo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用于注册时邮件激活 
 */
public interface ActiveCodeMapper {
	/**
	 * 将发送给邮箱的信息保存到数据库中
	 * @param email 只需要传入邮箱地址即可
	 * @return 
	 */
	int addActiveCode(@Param("email") String email, @Param("activeCode") String activeCode);

	/**
	 * 按照邮箱查找激活码
	 * @param email
	 * @return
     */
	List<String> findActiveCodeByEmail(String email);

    /**
     * 按照email和used查找code
     * @param activeCodeSo（email/used）
     * @return
     */
    List<String> findActiveCodeBySo(ActiveCodeSo activeCodeSo);
}
