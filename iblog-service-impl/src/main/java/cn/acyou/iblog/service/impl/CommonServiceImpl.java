package cn.acyou.iblog.service.impl;

import cn.acyou.iblog.constant.AppConstant;
import cn.acyou.iblog.exception.BussinessException;
import cn.acyou.iblog.redis.RedisUtil;
import cn.acyou.iblog.service.CommonService;
import cn.acyou.iblog.utility.AddressUtil;
import cn.acyou.iblog.utility.WeatherUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 公共模块的服务实现类
 *
 * @author youfang
 * @addTime 2017年7月17日 下午10:17:45
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {
    private static Logger log = Logger.getLogger(CommonServiceImpl.class);
    /**
     * 地址转换工具
     */
    @Resource
    private AddressUtil addressUtil;
    /**
     * 天气获取工具
     */
    @Resource
    private WeatherUtil weatherUtil;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据IP获取所属地天气。
     */
    public List<Map<String, Object>> getWeather(String ip) {
        List<Map<String, Object>> weatherinfo = new LinkedList<Map<String, Object>>();
        try {
            //如果IP是：0:0:0:0:0:0:0:1
            if (ip.equals("0:0:0:0:0:0:0:1")) {
                ip = AppConstant.NANJING_DIANXIN_IP;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            List<String> list = addressUtil.getAddress("ip=" + ip, "utf-8");
            log.info(list.get(0) + "," + list.get(1));
            String location = list.get(1).substring(0, 2);
            log.info(location);
            String weather;
            if (redisUtil.containsValueKey(location)) {
                weather = redisUtil.getValue(location);
            } else {
                //location存到redis缓存中，不需要每次都去查询。
                weather = weatherUtil.getWeather(location);
                redisUtil.cacheValue(location, weather, 86400000);
                log.info(weather);
            }
            String[] weatherInfos = weather.split("#");
            map.put("province", weatherInfos[0]);
            map.put("city", weatherInfos[1]);
            map.put("updateTime", weatherInfos[4]);
            map.put("temperature", weatherInfos[5]);
            map.put("weather", weatherInfos[6]);
            map.put("wind", weatherInfos[7]);
            map.put("image", weatherInfos[8]);
            map.put("todayScene", weatherInfos[10]);
            map.put("suggest", weatherInfos[11]);
            map.put("tomorrow", weatherInfos[12]);
            map.put("tomorrowWeather", weatherInfos[13]);
            weatherinfo.add(map);
//			for(String s:weatherInfos){
//				log.warn(s);
//			}
        } catch (Exception e) {
            throw new BussinessException("天气获取失败！");
        }

        return weatherinfo;
    }

}
