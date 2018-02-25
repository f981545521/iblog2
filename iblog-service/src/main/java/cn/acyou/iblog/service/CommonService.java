package cn.acyou.iblog.service;

import java.util.List;
import java.util.Map;

/**
 * 公共模块的服务
 * @author youfang
 * @addTime 2017年7月17日 下午9:15:42
 */
public interface CommonService {
	/**
	 * 从请求中获取IP，调用webservice解析为城市；在依据城市获取天气预报！
	 * @param ip 请求所属(源)IP
	 * @return 天气
	 */
	List<Map<String,Object>> getWeather(String ip);
}
