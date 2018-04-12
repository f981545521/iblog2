package cn.acyou.iblog.service.impl;

import cn.acyou.iblog.mappers.BossMapper;
import cn.acyou.iblog.model.test.Boss;
import cn.acyou.iblog.service.BossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author youfang
 * @date 2018-02-09 20:08
 **/
@Service("bosssService")
@Transactional
public class BossServiceImpl implements BossService {

    @Autowired
    private BossMapper bossMapper;

    @Override
    public List<Boss> getAllBoss() {
        return bossMapper.getAllTBoss();
    }
}
