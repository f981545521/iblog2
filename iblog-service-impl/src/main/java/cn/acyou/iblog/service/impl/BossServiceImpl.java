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
public class BossServiceImpl implements BossService {

    @Autowired
    private BossMapper bossMapper;

    @Override
    public List<Boss> getAllBoss() {
        return bossMapper.getAllTBoss();
    }

    @Override
    //@Transactional(rollbackFor = Exception.class)
    public int addBoss(Boss boss) {
        int result = bossMapper.addBoss(boss);
        //其他业务处理
        System.out.println("添加成功：" + boss);
        if (result == 1){
            throw new RuntimeException();
        }
        return result;
    }


}
