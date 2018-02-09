package cn.acyou.iblog.service.impl;

import cn.acyou.iblog.mappers.TBossMapper;
import cn.acyou.iblog.model.test.TBoss;
import cn.acyou.iblog.service.TBossService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author youfang
 * @date 2018-02-09 20:08
 **/
@Service("tBosssService")
public class TBossServiceImpl implements TBossService{

    @Resource
    private TBossMapper tBossMapper;

    @Override
    public List<TBoss> getAllBoss() {
        return tBossMapper.getAllTBoss();
    }
}
