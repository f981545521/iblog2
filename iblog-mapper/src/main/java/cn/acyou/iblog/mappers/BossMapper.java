package cn.acyou.iblog.mappers;

import cn.acyou.iblog.model.test.TBoss;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author youfang
 * @date 2018-02-09 20:20
 **/
public interface BossMapper {

    List<TBoss> getAllTBoss();
}