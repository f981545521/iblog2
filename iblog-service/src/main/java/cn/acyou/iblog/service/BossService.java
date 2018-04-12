package cn.acyou.iblog.service;

import cn.acyou.iblog.model.test.Boss;

import java.util.List;

/**
 * @author youfang
 * @date 2018-02-09 20:07
 **/
public interface BossService {

    List<Boss> getAllBoss();

    int addBoss(Boss boss);
}
